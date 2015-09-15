module Reel
  # TODO: split this into Git < Downloader class
  class Reel::Downloader
    attr_accessor :url, :repo,
                  :dir_path, :dir_name,
                  :github_user, :github_project

    def initialize(prefix, repo)
      @repo = repo
      @url = "https://github.com/#{@repo}"
      partition = @repo.partition("/")
      @github_user = partition[0]
      @github_project = @dir_name = partition[2]
      # TODO: or should this be simply prefix + @dir_name?
      @dir_path = File.join(prefix, @github_user, @dir_name)
    end

    def clone
      return if Dir.exist?(@dir_path)
      `git clone --depth 1 #{@url} #{@dir_path}`
    end

    # TODO: why doesnt s = -EOS.undent not work???
    # http://monksealsoftware.com/multi-line-strings-in-ruby/
    def to_str
      %(
        repo: #{@repo}
        github_user: #{@github_user}
        dir_name: #{@dir_name} = github_project: #{@github_project}
        url: #{@url}
        dir_path: #{@dir_path})
    end
  end
end
