require 'rubygems'
require 'fileutils'
require 'parallel'
require 'pathname'
require 'dotstrap/utils'
require 'dotstrap/git'
require 'dotstrap/shell'

module Dotstrap
  NAME        = 'dotstrap'
  NAME_EXE    = 'ds'
  VERSION     = '0.5.2'
  AUTHOR      = 'William Myers'
  EMAIL       = 'mkwmms@icloud.com'
  HOMEPAGE    = 'http://github.com/mkwmms/dotstrap'
  SUMMARY     = %(bootstrap your shell dotfiles in parallel from GitHub repos)
  DESCRIPTION = %(bootstrap your shell dotfiles in parallel from GitHub repos)
  # DESCRIPTION = 'downloads repositories from GitHub in parallel and ' \
                # 'symbolically links and/or creates a file to be sourced ' \
                # 'according to your shell (fish, zsh, bash)'

  class Dotstrap::Configuration
    attr_accessor :repos

    def initialize(repos = nil)
      @repos = repos unless repos.nil?
      Dotstrap.make_config_home
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
      repos.each do |repo|
        bundle = Dotstrap::Git.new(repo)
        next unless Dir.exist?(bundle.repo_path)
        puts repo
        puts bundle.url
        puts bundle.repo_path
        # shell = Dotstrap::Shell.new(repo_path)
        # TODO: only output all associated files on --verbose
        # shell.list(repo_path)
        puts "\n"
      end
    end

    def load_configs(repo_path)
      shell = ''
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
