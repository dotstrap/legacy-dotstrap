require 'parallel'
require_relative 'git'

module Reel
  class Reel::Update
    attr_accessor :repos, :reel_config_home

    # TODO: add progress bar https://github.com/grosser/parallel#progress--eta
    def initialize(reel_config_home, repos = false)
      @reel_config_home = reel_config_home
      # FIXME: why cant I use |=?
      if repos
        @repos = repos
      else
        # @repos = Dir.glob(@reel_config_home)
        @repos = Dir.glob(File.join(@reel_config_home, '*'))
      end
    end

    def update(repos = @repos)
      @repos = repos.select { |r| Dir.exist?(r) }
      # TODO: use number of processes/threads = to procs
      Parallel.map(repos, in_processes: 16) do |r|
        update = Reel::Git.new(@reel_config_home, r)
        update.pull
      end
    end
  end
end
