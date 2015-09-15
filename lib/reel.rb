require 'docopt'
require 'colorize'
require 'reel/version'
require 'reel/bundle'
require 'pathname'

module Reel
  @app = File.basename(__FILE__).chomp('.rb')

  # TODO: why is .undent not working??
  @doc = <<DOCOPT
#{@app.blue}

Copyright (C) 2015 William Myers https://github.com/mkwmms

#{'Usage:'.blue}
  #{@app} [options] bundle REPO ...
  #{@app} [options] update [REPO ...]

#{'Arguements:'.blue}
  REPO  The GitHub repo (in the form mkwmms/reel or full URL) to download

#{'Options:'.blue}
  -h --help     show this help message and exit
  -V --version  show version and exit
  -q --quiet    suppress output from Git etc.
DOCOPT

  def self.verbose?
    @args['--quiet'] == false || @args.nil? ? true : false
  end

  def self.reel_config_home
    config_dir = ENV.fetch('XDG_CONFIG_HOME', Dir.home)
    env_config_dir = ENV.fetch('REEL_CONFIG_HOME')
    if env_config_dir
      env_config_dir
    elsif config_dir == Dir.home
      File.join(config_dir, '.config', 'reel')
    else
      File.join(config_dir, 'reel')
    end
  end

  def self.parse_cli
    version_msg = "#{@app} #{Reel::VERSION}"
    @args = Docopt.docopt(@doc, version: version_msg)
    puts @args
    if @args['bundle']
      prefix = reel_config_home
      FileUtils.mkdir_p prefix
      bundle = Reel::Bundle.new(prefix, @args['REPO'])
      bundle.download
    end
  rescue Docopt::Exit, RegexpError => e
    puts e.message
  end
end

# TODO: add options for parsing a file containing repos
# TODO: implement downloading a regular URL
# TODO: add fish, zsh, and bash support (link and/or source files)
# TODO: test
