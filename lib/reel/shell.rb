module Reel
  class Reel::Shell
    # FIXME: rename method vars so the dont clash with methods
    attr_accessor :repo_path, :repo_config_files,
                  :shell_name, :shell_config_home, :reel_config_file

    # TODO: how to handle load order? ie. load path.fish first
    def initialize(repo_path, dest_dir = nil)
      @repo_path = repo_path
      @repo_config_files = []
      @shell_name = shell_name
      if dest_dir == nil
        @shell_config_home = shell_config_home
      else
        @shell_config_home = dest_dir
      end
      @reel_config_file = config_file(@shell_name, Reel.reel_config_home)
    end

    def configure(src_dir = @repo_path)
      # puts src_dir
      repo_dir = src_dir
      # repo_dir = File.join(@reel_config_home, src_dir)
      if @shell_name == 'fish'
        fish_functions(repo_dir).each do |f|
          link_config_file(f, File.join(fish_config_home, 'functions'))
        end

        fish_completions(repo_dir).each do |f|
          link_config_file(f, File.join(fish_config_home, 'completions'))
        end

        @repo_config_files = fish_general_configs(repo_dir)
        unless @repo_config_files.empty?
          @repo_config_files.each do |file|
            write_config_file(file, @reel_config_file)
          end
        end
      else
        fail ArgumentError, 'zsh & bash support coming soon!'
      end
    end

    def write_config_file(file_to_source, reel_config_file = config_file)
      source_str = "source \"#{file_to_source}\"\n"
      if File.exist?(reel_config_file)
        return if File.readlines(reel_config_file).grep(source_str).any?
      end
      # TODO: how to append to file in one line (without open statement)
      # File.write(reel_config_file, "source \"#{file_to_source}\"\n", 0)
      File.open(reel_config_file, 'a') do |f|
        f.write source_str
      end
    end

    def link_config_file(src, dest_dir)
      dst = File.join(dest_dir, File.basename(src))
      FileUtils.ln_s src, dst, force: true
    end

    def config_file(sh, dir = Reel.reel_config_home)
      File.join(dir, "config.#{sh}")
    end

    def shell_name
      # FIXME: bad to rely on SHELL environment variable to determine shell?
      shell_env_var = ENV['SHELL'].downcase
      @shell_name =
        if shell_env_var.include?('fish')
          'fish'
        elsif shell_env_var.include?('zsh')
          'zsh'
        elsif shell_env_var.include?('bash')
          'bash' # TODO: should bash have an extension of .sh?
        else
          # TODO: cleanup error output
          fail ArgumentError, "#{shell_env_var} is an unsupported shell."
        end
    end

    def shell_config_home(shell_name = @shell_name)
      case shell_name.downcase
      when 'fish'
        fish_config_home
      else
        fail 'Fish is the only suported shell at the moment'
      end
    end

    private

    def fish_config_home
      config_home = ENV.fetch('XDG_CONFIG_HOME', File.expand_path('~/.config'))
      File.join(config_home, 'fish')
    end

    # TODO: combine fish_*(dir) methods into 1?
    def fish_general_configs(dir)
      Dir.glob(File.join(dir, '*.fish'))
    end

    def fish_functions(dir)
      Dir.glob(File.join(dir, 'functions', '*.fish'))
    end

    def fish_completions(dir)
      Dir.glob(File.join(dir, 'completions', '*.fish'))
    end
  end
end
