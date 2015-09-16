require 'parallel'
require 'reel/git'
require 'reel/shell'

module Reel
  class Reel::Bundle
    attr_accessor :repos, :reel_config_home

    # TODO: add progress bar https://github.com/grosser/parallel#progress--eta
    def initialize(reel_config_home, repos)
      @repos = repos
      @reel_config_home = reel_config_home
    end

    def download(repos = @repos)
      Parallel.map(repos, in_threads: 16) do |r|
        bundle = Reel::Git.new(@reel_config_home, r)
        bundle.clone
        load_configs([bundle.repo_path])
      end
    end

    def load_configs(repo_path)
      shell = ''
      repo_path.each do |r|
        shell = Reel::Shell.new(@reel_config_home, r)
        shell.configure(repo_path)
        # TODO: cleanup CLI output
        puts "Make sure to `source \"#{shell.reel_config_file}\"` in your shell " \
          "startup file" unless shell.repo_config_files.empty?
      end
    end
  end
end
