require 'parallel'
require 'fileutils'
require_relative 'git'
require_relative 'shell'

# TODO: combine Dotstrap::Configuration with Dotstrap module?
module Dotstrap
  class Dotstrap::Configuration
    attr_accessor :repos

    def initialize(repos = nil)
      @repos = repos unless repos.nil?
      Dotstrap.make_dotstrap_config_home
    end

    def configure(dest_dir = Dotstrap.dotstrap_config_home, repos = @repos)
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
      if repos.nil? || repos == 0 || repos.empty?
        dotstrap_config_files = File.join(Dotstrap.dotstrap_config_home, '**')
        Dir.glob(dotstrap_config_files).each_with_index do |repo_path, index|
          next unless repo_path.include?('-') && File.directory?(repo_path)
          repo = File.basename(repo_path)
          repo[repo.index('-')] = '/'
          puts "\n" unless index == 1
          puts repo
          puts Dotstrap::Git.new(repo).url
          puts repo_path
        end
      else
        repos.each do |repo|
          puts repo
          bundle = Dotstrap::Git.new(repo)
          puts bundle.url
          repo_path = bundle.repo_path
          puts repo_path
          # shell = Dotstrap::Shell.new(repo_path)
          # TODO: only output all associated files on --verbose
          # shell.list(repo_path)
          puts "\n"
        end
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
