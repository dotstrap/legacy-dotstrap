require 'parallel'
require 'reel/downloader'

module Reel
  class Reel::Bundle
    attr_accessor :repos, :prefix

    # TODO: add progress bar https://github.com/grosser/parallel#progress--eta
    def initialize(prefix, repos)
      @repos = repos
      @prefix = prefix
    end

    def download
      # TODO: use number of processes/threads = to procs
      Parallel.map(@repos, :in_threads=>16) do |r|
        bundle = Reel::Downloader.new(@prefix, r)
        bundle.clone
      end
    end
  end
end
