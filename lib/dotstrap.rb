require 'rubygems'
require 'commander/import'
require 'pathname'
require_relative 'dotstrap/configuration'

module Dotstrap
  NAME        = 'dotstrap'
  VERSION     = '0.5.0'
  AUTHOR      = 'William Myers'
  EMAIL       = 'mkwmms@icloud.com'
  HOMEPAGE    = 'http://github.com/mkwmms/dotstrap'
  SUMMARY     = %(Bootstrap your shell dotfiles in parallel!)
  DESCRIPTION = 'Downloads repositories from GitHub in parallel and ' \
                'symbolically links and/or creates a file to be sourced ' \
                'according to your shell (fish, zsh, bash)'

  class << self
    def dotstrap_config_home
      config_dir = ENV.fetch('XDG_CONFIG_HOME', Dir.home)
      env_config_dir = ENV['DOTSTRAP_CONFIG_HOME']
      if env_config_dir
        env_config_dir
      elsif config_dir == Dir.home
        File.join(config_dir, '.config', 'dotstrap')
      else
        File.join(config_dir, 'dotstrap')
      end
    end

    def make_dotstrap_config_home
      FileUtils.mkdir_p dotstrap_config_home
    end
  end
end

# TODO: implement downloading a regular URL
