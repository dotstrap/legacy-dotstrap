require 'rubygems'
require 'fileutils'
require 'parallel'
require 'pathname'
require_relative 'dotstrap/git'
require_relative 'dotstrap/shell'

module Dotstrap
  NAME        = 'dotstrap'
  EXE_NAME    = 'dotstrap'
  VERSION     = '0.2.0'
  AUTHOR      = 'William Myers'
  HOMEPAGE    = 'http://github.com/mkwmms/dotstrap'
  SOURCE_URL  = 'http://github.com/mkwmms/dotstrap'
  DOC_URL     = 'http://www.rubydoc.info/github/mkwmms/dotstrap/master/Dotstrap'
  SUMMARY     = 'bootstrap your shell dotfiles in parallel from GitHub repos'
  DESCRIPTION = 'bootstrap your shell dotfiles in parallel from GitHub repos'
  # DESCRIPTION = 'downloads repositories from GitHub in parallel and ' \
                # 'symbolically links and/or creates a file to be sourced ' \
                # 'according to your shell (fish, zsh, bash)'

  class << self
    require 'pathname'

    def config_home
      return ENV['DOTSTRAP_CONFIG_HOME'] if ENV.has_key?('DOTSTRAP_CONFIG_HOME')

      config_dir = ENV.fetch('XDG_CONFIG_HOME', Dir.home)
      if config_dir == Dir.home
        File.join(config_dir, '.config', 'dotstrap')
      else
        File.join(config_dir, 'dotstrap')
      end
    end

    def shell_name
      # FIXME: bad to rely on SHELL environment variable to determine shell?
      shell =
        case ENV['SHELL']
        when %r{/(ba)?sh} then
          'bash'
        when %r{/zsh} then
          'zsh'
        when %r{/fish} then
          'fish'
        else
          raise RuntimeError, ENV['SHELL']
        end
      $LOG.debug { "SHELL_NAME:#{shell}" }
      shell
    end

    def shell_profile(shell = shell_name)
      profile =
        case shell
        when 'bash' then
          bash_profile = ''
          ['.bash_profile' '.profile'].each do |file|
            bash_profile = File.join(Dir.home, file)
            break if File.exist?(bash_profile)
          end
          bash_profile
        when 'zsh' then
          File.join(Dir.home, '.zshrc')
        when 'fish' then
          File.join(Dir.home, '.config', 'fish', 'config.fish')
          # File.exist?(file) ? file : fail ShellProfileError, "Fish shell config file not found
        end
      $LOG.debug { "SHELL_PROFILE:#{profile}" }
      profile.strip unless profile.nil?
    end

    def shell_config_home(shell = shell_name)
      h = Pathname.new(shell_profile(shell)).parent
      $LOG.debug { "SHELL_CONFIG_HOME:#{h}" }
      h
    end

    def config_file(shell = shell_name)
      File.join(config_home, "config.#{shell}")
    end

    def installed_repo_paths
      Dir[File.join(config_home, '**')]
    end

    def installed_repos
      repos = []
      installed_repo_paths.each do |repo_path|
        # next unless repo_path.include?('-') && File.directory?(repo_path)
        repo = File.basename(repo_path)
        repos << repo.sub(/-/,'/')
      end
      repos
    end
  end

  class Dotstrap::Configuration
    require 'fileutils'

    attr_accessor :repos

    def initialize(repos = nil)
      @repos = repos unless repos.nil?
      FileUtils.mkdir_p(Dotstrap.config_home)
    end

    def configure(dest_dir = Dotstrap.config_home, repos = @repos)
      if Dotstrap.shell_name == 'fish'
        fish_config_home = Dotstrap.shell_config_home('fish')
        FileUtils.mkdir_p(File.join(fish_config_home, 'functions'))
        FileUtils.mkdir_p(File.join(fish_config_home, 'completions'))
      end

      Parallel.map(repos, in_threads: 16) do |r|
        bundle = Dotstrap::Git.new(r, dest_dir)
        path = bundle.clone
        puts path
        load_configs([path]) if path
      end
    end

    def remove(repos = @repos)
      repos.each do |repo|
        bundle = Dotstrap::Git.new(repo)
        repo_path = bundle.repo_path
        shell = Dotstrap::Shell.new(repo_path)
        shell.unconfigure(repo_path)
      end
    end

    # TODO: return a data-structure from `list`, do not print out
    def list(repos = @repos)
      repos.each do |repo|
        bundle = Dotstrap::Git.new(repo)
        next unless Dir.exist?(bundle.repo_path)
        puts "#{'=>'.colorize(:blue)} #{repo}"
        puts bundle.url
        puts bundle.repo_path
        # TODO: only output all associated files on --verbose
        puts "\n"
      end
    end

    def load_configs(repo_path)
      repo_path.each do |r|
        shell = Dotstrap::Shell.new(r)
        shell.configure(repo_path)
      end
    end
  end
end
