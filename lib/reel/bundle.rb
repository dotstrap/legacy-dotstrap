require 'parallel'
require 'fileutils'
require 'reel/git'
require 'reel/shell'

module Reel
  class Reel::Bundle
    attr_accessor :repos

    def initialize(repos = nil)
      @repos = repos unless repos.nil?
      Reel.make_reel_config_home
    end

    def download(dest_dir = Reel.reel_config_home, repos = @repos)
      FileUtils.mkdir_p dest_dir
      Parallel.map(repos, in_threads: 16) do |r|
        bundle = Reel::Git.new(r)
        bundle.clone
        load_configs([bundle.repo_path])
      end
    end

    def remove(repos = @repos)
      repos.each do |repo|
        bundle = Reel::Git.new(repo)
        repo_path = bundle.repo_path
        shell = Reel::Shell.new(repo_path)
        shell.unconfigure(repo_path)
      end
    end

    def list(repos = @repos)
      if repos.nil? || repos == 0 || repos.empty?
        Dir.glob(File.join(Reel.reel_config_home, '**')).each do |repo_path|
          next unless repo_path.include?('-') && File.directory?(repo_path)
          repo = File.basename(repo_path)
          repo[repo.index('-')] = '/'
          puts repo
          puts Reel::Git.new(repo).url
          puts repo_path
          puts "\n"
        end
      else
        repos.each do |repo|
          puts repo
          bundle = Reel::Git.new(repo)
          puts bundle.url
          repo_path = bundle.repo_path
          puts repo_path
          # shell = Reel::Shell.new(repo_path)
          # TODO: only output all associated files on --verbose
          # shell.list(repo_path)
          puts "\n"
        end
      end
    end

    def load_configs(repo_path)
      shell = ''
      repo_path.each do |r|
        shell = Reel::Shell.new(r)
        shell.configure(repo_path)
        # TODO: cleanup CLI output; only output `make sure to source...` once
        puts "Make sure to `source \"#{shell.reel_config_file}\"` in your shell " \
          "startup file" unless shell.repo_config_files.empty?
      end
    end
  end
end
