# Copyright 2009-2015 Dotstrap contributors.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions
# are met:
#
#   1. Redistributions of source code must retain the above copyright
#      notice, this list of conditions and the following disclaimer.
#   2. Redistributions in binary form must reproduce the above copyright
#      notice, this list of conditions and the following disclaimer in the
#      documentation and/or other materials provided with the distribution.
#
# THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
# IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
# OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
# IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
# INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
# NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
# DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
# THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
# THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

class Tty
  class << self
    def blue
      bold 34
    end

    def white
      bold 39
    end

    def red
      underline 31
    end

    def yellow
      underline 33
    end

    def reset
      escape 0
    end

    def em
      underline 39
    end

    def green
      bold 32
    end

    def gray
      bold 30
    end

    def highlight
      bold 39
    end

    def width
      `/usr/bin/tput cols`.strip.to_i
    end

    def truncate(str)
      str.to_s[0, width - 4]
    end

    private

    def color(n)
      escape "0;#{n}"
    end

    def bold(n)
      escape "1;#{n}"
    end

    def underline(n)
      escape "4;#{n}"
    end

    def escape(n)
      "\033[#{n}m" if $stdout.tty?
    end
  end
end

def ohai(title, *sput)
  title = Tty.truncate(title) if $stdout.tty? && !ARGV.verbose?
  puts "#{Tty.blue}==>#{Tty.white} #{title}#{Tty.reset}"
  puts sput
end

def oh1(title)
  title = Tty.truncate(title) if $stdout.tty? && !ARGV.verbose?
  puts "#{Tty.green}==>#{Tty.white} #{title}#{Tty.reset}"
end

# Print a warning (do this rarely)
def opoo(warning)
  $stderr.puts "#{Tty.yellow}Warning#{Tty.reset}: #{warning}"
end

def onoe(error)
  $stderr.puts "#{Tty.red}Error#{Tty.reset}: #{error}"
end

def ofail(error)
  onoe error
  Dotstrap.failed = true
end

def odie(error)
  onoe error
  exit 1
end

def pretty_duration(s)
  return "2 seconds" if s < 3 # avoids the plural problem ;)
  return "#{s.to_i} seconds" if s < 120
  "%.1f minutes" % (s/60)
end

def plural(n, s = "s")
  (n == 1) ? "" : s
end

# prints no output
def quiet_system(cmd, *args)
  Dotstrap._system(cmd, *args) do
    # Redirect output streams to `/dev/null` instead of closing as some programs
    # will fail to execute if they can't write to an open stream.
    $stdout.reopen("/dev/null")
    $stderr.reopen("/dev/null")
  end
end

def which(cmd, path = ENV["PATH"])
  path.split(File::PATH_SEPARATOR).each do |p|
    begin
      pcmd = File.expand_path(cmd, p)
    rescue ArgumentError
      # File.expand_path will raise an ArgumentError if the path is malformed.
      # See https://github.com/Dotstrap/DOTSTRAP/issues/32789
      next
    end
    return Pathname.new(pcmd) if File.file?(pcmd) && File.executable?(pcmd)
  end
  nil
end

def which_editor
  editor = ENV.values_at("DOTSTRAP_EDITOR", "VISUAL", "EDITOR").compact.first
  return editor unless editor.nil?

  # Find Textmate
  editor = "mate" if which "mate"
  # Find BBEdit / TextWrangler
  editor ||= "edit" if which "edit"
  # Find vim
  editor ||= "vim" if which "vim"
  # Default to standard vim
  editor ||= "/usr/bin/vim"

  opoo <<-EOS.undent
    Using #{editor} because no editor was set in the environment.
    This may change in the future, so we recommend setting EDITOR, VISUAL,
    or DOTSTRAP_EDITOR to your preferred text editor.
  EOS

  editor
end

def exec_editor(*args)
  safe_exec(which_editor, *args)
end

def exec_browser(*args)
  browser = ENV["DOTSTRAP_BROWSER"] || ENV["BROWSER"] || OS::PATH_OPEN
  safe_exec(browser, *args)
end

def safe_exec(cmd, *args)
  # This buys us proper argument quoting and evaluation
  # of environment variables in the cmd parameter.
  exec "/bin/sh", "-c", "#{cmd} \"$@\"", "--", *args
end

def ignore_interrupts(opt = nil)
  std_trap = trap("INT") do
    puts "One sec, just cleaning up" unless opt == :quietly
  end
  yield
ensure
  trap("INT", std_trap)
end

def nostdout
  if ARGV.verbose?
    yield
  else
    begin
      out = $stdout.dup
      $stdout.reopen("/dev/null")
      yield
    ensure
      $stdout.reopen(out)
      out.close
    end
  end
end

def paths
  @paths ||= ENV["PATH"].split(File::PATH_SEPARATOR).collect do |p|
    begin
      File.expand_path(p).chomp("/")
    rescue ArgumentError
      onoe "The following PATH component is invalid: #{p}"
    end
  end.uniq.compact
end

module Dotstrap
  def self._system(cmd, *args)
    pid = fork do
      yield if block_given?
      args.collect!(&:to_s)
      exec(cmd, *args) rescue nil
      exit! 1 # never gets here unless exec failed
    end
    Process.wait(pid)
    $?.success?
  end

  def self.system(cmd, *args)
    puts "#{cmd} #{args*" "}" if ARGV.verbose?
    _system(cmd, *args)
  end

  def config_home
    return ENV['DOTSTRAP_CONFIG_HOME'] if ENV.has_key?(DOTSTRAP_CONFIG_HOME)

    config_dir = ENV.fetch('XDG_CONFIG_HOME', Dir.home)
    if config_dir == Dir.home
      File.join(config_dir, '.config', 'dotstrap')
    else
      File.join(config_dir, 'dotstrap')
    end
  end

  def make_config_home
    FileUtils.mkdir_p config_home
  end

  def shell_name
    # FIXME: bad to rely on SHELL environment variable to determine shell?
    case ENV['SHELL']
      when %r{/(ba)?sh} then
        'bash'
      when %r{/zsh} then
        'zsh'
      when %r{/fish} then
        'fish'
      # else
        # raise UnsupportedShellError(ENV['SHELL'])
    end
  end

  def config_file(shell = shell_name)
    File.join(config_home, "config.#{shell}")
  end

  def installed_repo_paths
    Dir[File.join(config_home, '**')]
  end

  def installed_repos
    repos = []
    repo_config_dir_paths.each do |repo_path|
      repos << File.basename(repo_path)
    end
    repos
  end

  # return the shell profile file based on users' preference shell
  def shell_profile
    case shell_name
      when 'bash' then
        %s[.bash_profile .profile].each do |file|
          profile = File.join(Dir.home, file)
          return profile if File.exist?(profile)
        end
      when 'zsh' then
        profile = File.join(Dir.home, '.zshrc')
        return profile if File.exist?(profile)
      when 'fish' then
        profile = File.join(Dir.home, '.config', 'fish', 'config.fish')
        return profile if File.exist?(profile)
      # File.exist?(file) ? file : fail ShellProfileError, "Fish shell config file not found
    end
  end
end
