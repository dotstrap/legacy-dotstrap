require 'rubygems'
require 'fileutils'
require 'parallel'
require 'pathname'
require_relative 'dotstrap/git'
require_relative 'dotstrap/shell'

module Dotstrap
  NAME        = 'dotstrap'
  EXE_NAME    = 'ds'
  VERSION     = '0.5.4'
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
      case ENV['SHELL']
        when %r{/(ba)?sh} then
          'bash'
        when %r{/zsh} then
          'zsh'
        when %r{/fish} then
          'fish'
        # else
        # raise UnsupportedShellError(ENV['SHELL'])
      end
    end

    # return the shell profile file based on users' preference shell
    def shell_profile(shell = shell_name)
      profile =
        case shell
        when 'bash' then
          %s[.bash_profile .profile].each do |file|
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
      profile.strip
    end

    def shell_config_home(shell = shell_name)
      Pathname.new(shell_profile(shell)).parent
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
      fish_config_home = Dotstrap.shell_config_home('fish')
      if Dir.exist?(fish_config_home)
        FileUtils.mkdir_p(File.join(fish_config_home, 'functions'))
        FileUtils.mkdir_p(File.join(fish_config_home, 'completions'))
      end

      Parallel.map(repos, in_threads: 16) do |r|
        bundle = Dotstrap::Git.new(r, dest_dir)
        bundle.clone
        load_configs([bundle.repo_path])
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
        # puts "" unless index == 0
        puts repo
        puts bundle.url
        puts bundle.repo_path
        # TODO: only output all associated files on --verbose
        # shell = Dotstrap::Shell.new(repo_path)
        # shell.list(repo_path)
        puts "\n"
      end
    end

    def load_configs(repo_path)
      repo_path.each do |r|
        shell = Dotstrap::Shell.new(r)
        shell.configure(repo_path)
        # puts "Make sure to `source \"#{shell.dotstrap_config_file}\"` in your shell " \
        # "startup file" unless shell.repo_config_files.empty?
      end
    end
  end
end

# TODO: implement downloading a regular URL
