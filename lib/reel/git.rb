module Reel
  # TODO: split this into Git < Downloader class
  class Reel::Git
    attr_accessor :url, :repo,
                  :repo_path, :repo_name,
                  :github_user, :github_project

    def initialize(repo, src_dir = Reel.reel_config_home)
      @repo = repo
      @url = "https://github.com/#{@repo}"
      partition = @repo.partition("/")
      @github_user = partition[0]
      @github_project = @repo_name = partition[2]
      @repo_path = File.join(src_dir, "#{@github_user}-#{@repo_name}")
    end

    # TODO: optionally silence output of git clone
    def clone(url = @url, dir = @repo_path)
      # TODO: prompt user for what to do if dir exists before cloning?
      # return if Dir.exist?(@repo_path)
      # FIXME: do not rely on /dev/null redirection to silence git output
      FileUtils.rm_rf dir
      `git clone --depth 1 #{url} #{dir} &> /dev/null`
      # system 'git', 'clone', '--depth', '1', "#{@url}", "#{@repo_path}"
    end

    def pull(dir = @repo_path)
      return unless File.directory?(dir)
      Dir.chdir dir
      `cd #{dir} && git pull`
    end

    def to_str
      %(
        repo: #{@repo}
        github_user: #{@github_user}
        repo_name: #{@repo_name} = github_project: #{@github_project}
        url: #{@url}
        repo_path: #{@repo_path})
    end
  end
end
