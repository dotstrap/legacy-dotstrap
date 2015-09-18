require 'parallel'
require 'fileutils'
require 'reel/git'
require 'reel/shell'

module Reel
  class Reel::Bundle
    attr_accessor :repos

    # TODO: add progress bar https://github.com/grosser/parallel#progress--eta
    def initialize(repos)
      @repos = repos
    end

    # FIXME: which parameter takes presidence when both are optional and one is
    # passed?
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

    def load_configs(repo_path)
      shell = ''
      repo_path.each do |r|
        shell = Reel::Shell.new(r)
        shell.configure(repo_path)
        # TODO: cleanup CLI output
        puts "Make sure to `source \"#{shell.reel_config_file}\"` in your shell " \
          "startup file" unless shell.repo_config_files.empty?
      end
    end
  end
end
