require 'colorize'
require 'logger'

module Dotstrap
  # TODO: split this into Git < Downloader class
  class Dotstrap::Git
    attr_accessor :url, :repo, :repo_path, :repo_owner, :repo_name

    def initialize(repo, dest_dir = Dotstrap.config_home)
      @repo = determine_repo_slug(repo)
      # TODO: allow an option to specify to download with SSH or HTTPs from Git
      @url = determine_url(@repo)
      @repo_owner, @repo_name = split_repo_slug(@repo)
      # @repo_path = File.join(dest_dir, "#{@repo_owner}.#{@repo_name}")
      @repo_path = File.join(dest_dir, "#{@repo_owner}-#{@repo_name}")
      # @repo_path = File.join(dest_dir, @github_user, @repo_name)
    end

    # FIXME: if user is not logged in to Git the prompt for username/password
    # is mangled because the threads are not synchronized
    def clone(url = @url, repo_path = @repo_path, repo = @repo)
      return pull(repo_path) if Dir.exist?(repo_path)
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

    def determine_repo_slug(repo)
      return repo unless repo.start_with?("http", "git@")
      repo = repo.gsub(/(https?:\/\/github.com\/)|(git@github.com:)/,"")
      repo = repo.gsub(/.git/,"")
    end

    def determine_url(repo)
      return repo if repo.start_with?("http", "git@")
      "https://github.com/#{repo}"
    end

    def split_repo_slug(repo, sep = "/")
      partition = repo.partition(sep)
      # TODO: make dotstrap ansible role compatible
      # return partition[0], partition[2].gsub("ansible-", "")
      return partition[0], partition[2].gsub("ansible-", "")
    end

    def git_verbosity
      if $LOG.debug?
        '--verbose'
      else
        return '--quiet'
      end
    end
  end
end
