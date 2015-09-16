require 'parallel'
require 'reel/git'

module Reel
  class Reel::Update
    attr_accessor :repos, :reel_config_home

    # TODO: add progress bar https://github.com/grosser/parallel#progress--eta
    def initialize(reel_config_home, repos = nil)
      @reel_config_home = reel_config_home
      @repos |= Dir.glob(@reel_config_home)
    end

    def update(repos = @repos)
      # TODO: use number of processes/threads = to procs
      Parallel.map(repos, :in_threads=>16) do |r|
        update = Reel::Git.new(@reel_config_home, r)
        update.pull
      end
    end

  end
end
