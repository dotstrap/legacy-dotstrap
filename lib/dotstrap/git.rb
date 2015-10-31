module Dotstrap
  # TODO: split this into Git < Downloader class
  class Dotstrap::Git
    attr_accessor :url, :repo,
                  :repo_path, :repo_name,
                  :github_user, :github_project

    def initialize(repo, src_dir = Dotstrap.dotstrap_config_home)
      @repo = repo
      @url = "https://github.com/#{@repo}"
      partition = @repo.partition("/")
      @github_user = partition[0]
      @github_project = @repo_name = partition[2]
      @repo_path = File.join(src_dir, "#{@github_user}-#{@repo_name}")
    end

    def clone(url = @url, dir = @repo_path, repo = @repo)
      if Dir.exist?(dir)
        pull(dir)
        return
      end
      `git clone -q #{url} #{dir}`
      puts "#{repo} [downloaded]"
    end

    def pull(dir = @repo_path, repo = @repo)
      `git pull -q`
       puts "#{repo} [updated]"
    end
  end
end
