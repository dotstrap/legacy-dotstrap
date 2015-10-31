require 'rubygems'
require 'commander/import'
require 'pathname'
require_relative 'reel/configuration'

module Reel
  NAME        = 'reel'
  VERSION     = '0.4.0'
  AUTHOR      = 'William Myers'
  EMAIL       = 'mkwmms@icloud.com'
  HOMEPAGE    = 'http://github.com/mkwmms/reel'
  SUMMARY     = %(Bootstrap your shell dotfiles in parallel!)
  DESCRIPTION = 'Downloads repositories from GitHub in parallel and ' \
                'symbolically links and/or creates a file to be sourced ' \
                'according to your shell (fish, zsh, bash)'

  class << self
    def reel_config_home
      config_dir = ENV.fetch('XDG_CONFIG_HOME', Dir.home)
      env_config_dir = ENV['REEL_CONFIG_HOME']
      if env_config_dir
        env_config_dir
      elsif config_dir == Dir.home
        File.join(config_dir, '.config', 'reel')
      else
        File.join(config_dir, 'reel')
      end
    end

    def make_reel_config_home
      FileUtils.mkdir_p reel_config_home
    end
  end
end

# TODO: implement downloading a regular URL
