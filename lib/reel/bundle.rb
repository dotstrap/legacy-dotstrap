require 'parallel'
require 'reel/git'
require 'reel/shell'

module Reel
  class Reel::Bundle
    attr_accessor :repos

    # TODO: add progress bar https://github.com/grosser/parallel#progress--eta
    def initialize(repos)
      @repos = repos
    end

    def download(dest_dir = Reel.reel_config_home, repos = @repos)
      Dir.mkdir_p dest_dir
      Parallel.map(repos, in_threads: 16) do |r|
        bundle = Reel::Git.new(dest_dir, r)
        bundle.clone
        load_configs([bundle.repo_path])
      end
    end

    def load_configs(dest_dir = Reel.reel_config_home, repo_path)
      shell = ''
      repo_path.each do |r|
        shell = Reel::Shell.new(dest_dir, r)
        shell.configure(repo_path)
        # TODO: cleanup CLI output
        puts "Make sure to `source \"#{shell.reel_config_file}\"` in your shell " \
          "startup file" unless shell.repo_config_files.empty?
      end
    end
  end
end
