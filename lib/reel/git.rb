module Reel
  # TODO: split this into Git < Downloader class
  class Reel::Git
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
      # @dir_path = File.join(prefix, "#{@github_user}-#{@dir_name}")
      @dir_path = File.join(prefix, @github_user, @dir_name)
    end

    # TODO: optionally silence output of git clone
    def clone
      # TODO: prompt user for what to do if dir exists before cloning?
      # return if Dir.exist?(@dir_path)
      # FIXME: do not rely on /dev/null redirection to silence git output
      FileUtils.rm_rf @dir_path
      `git clone --depth 1 #{@url} #{@dir_path} &> /dev/null`
      # system 'git', 'clone', '--depth', '1', "#{@url}", "#{@dir_path}"
    end

    def pull(dir = @dir_path)
      return unless Dir.exist?(dir)
      Dir.chdir dir
      `git pull`
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
