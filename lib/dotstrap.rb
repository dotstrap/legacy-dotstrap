require 'rubygems'
require 'fileutils'
require 'parallel'
require 'pathname'
require_relative 'dotstrap/git'
require_relative 'dotstrap/utils'
require_relative 'dotstrap/shell'

module Dotstrap
  NAME        = 'dotstrap'
  EXE_NAME    = 'ds'
  VERSION     = '0.5.2'
  AUTHOR      = 'William Myers'
  EMAIL       = 'mkwmms@icloud.com'
  HOMEPAGE    = 'http://github.com/mkwmms/dotstrap'
  SUMMARY     = %(bootstrap your shell dotfiles in parallel from GitHub repos)
  DESCRIPTION = %(bootstrap your shell dotfiles in parallel from GitHub repos)
  # DESCRIPTION = 'downloads repositories from GitHub in parallel and ' \
                # 'symbolically links and/or creates a file to be sourced ' \
                # 'according to your shell (fish, zsh, bash)'

  class << self
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

    # return the shell profile file based on users' preference shell
    def shell_profile(shell = shell_name)
      case shell
        when 'bash' then
          %s[.bash_profile .profile].each do |file|
            profile = File.join(Dir.home, file)
            return profile if File.exist?(profile)
          end
        when 'zsh' then
          profile = File.join(Dir.home, '.zshrc')
          return profile if File.exist?(profile)
        when 'fish' then
          profile = File.join(Dir.home, '.config', 'fish', 'config.fish')
          return profile if File.exist?(profile)
        # File.exist?(file) ? file : fail ShellProfileError, "Fish shell config file not found
      end
    end
  end

  class Dotstrap::Configuration
    attr_accessor :repos

    def initialize(repos = nil)
      @repos = repos unless repos.nil?
      FileUtils.mkdir_p(Dotstrap.config_home)
    end

    def configure(dest_dir = Dotstrap.config_home, repos = @repos)
      Parallel.map(repos, in_threads: 16) do |r|
        bundle = Dotstrap::Git.new(r)
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

    def list(repos = @repos)
      repos.each_with_index do |repo, index|
        bundle = Dotstrap::Git.new(repo)
        next unless Dir.exist?(bundle.repo_path)
        puts "" unless index == 0
        puts repo
        puts bundle.url
        puts bundle.repo_path
        # shell = Dotstrap::Shell.new(repo_path)
        # TODO: only output all associated files on --verbose
        # shell.list(repo_path)
        # puts "\n"
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
