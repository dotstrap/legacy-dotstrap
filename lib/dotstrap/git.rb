require 'colorize'
require 'logger'

module Dotstrap
  # TODO: split this into Git < Downloader class
  class Dotstrap::Git
    attr_accessor :url, :repo,
                  :repo_path, :repo_name,
                  :github_user, :github_project

    def initialize(repo, dest_dir = Dotstrap.config_home)
      @repo = repo
      # TODO: allow an option to specify to download with SSH or HTTPs from Git
      @url = "https://github.com/#{@repo}"
      partition = @repo.partition("/")
      @github_user = partition[0]
      @github_project = @repo_name = partition[2]
      @repo_path = File.join(dest_dir, "#{@github_user}-#{@repo_name}")
      # @repo_path = File.join(dest_dir, @github_user, @repo_name)
    end

    # FIXME: if user is not logged in to Git the prompt for username/password
    # is mangled because the threads are not synchronized
    def clone(url = @url, repo_path = @repo_path, repo = @repo)
      if Dir.exist?(repo_path)
        return pull(repo_path)
      end
      return false unless system('git', 'clone', *git_verbosity, url, repo_path)
      $LOG.debug { "CLONE #{repo} #{url} #{repo_path}" }
      $LOG.unknown { "#{'=> '.colorize(:blue)}#{repo}\nupdated" }
      repo_path
    end

    def pull(repo_path = @repo_path, repo = @repo)
      Dir.chdir(repo_path)
      return false unless system('git', 'pull', *git_verbosity, repo_path)
      $LOG.debug { "PULL #{repo} #{url} #{repo_path}" }
      $LOG.unknown { "#{'=> '.colorize(:blue)}#{repo}\nupdated" }
      repo_path
    end

    private

    def git_verbosity
      if $LOG.debug?
        '--verbose'
      else
        return '--quiet'
      end
    end
  end
end
