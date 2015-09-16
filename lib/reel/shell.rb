module Reel
  class Reel::Shell
    # TODO: make reel_config_home & reel_config_file a constant?
    attr_accessor :reel_config_home, :repo_path, :repo_config_files,
                  :shell_name, :reel_config_file

    # TODO: how to handle load order? ie. load path.fish first
    def initialize(reel_config_home, repo_path)
      @reel_config_home = reel_config_home
      @repo_path = repo_path
      @repo_config_files = []

      @shell_name = shell_type
      @reel_config_file = File.join(@reel_config_home, "config.#{@shell_name}")
    end

    def fish_config_home
      config_home = ENV.fetch('XDG_CONFIG_HOME', File.expand_path('~/.config'))
      File.join(config_home, 'fish')
    end

    def configure(dir = @repo_path)
      reel_config_dir = File.join(@reel_config_home, dir)
      if @shell_name == 'fish'
        fish_functions(reel_config_dir).each do |f|
          link_config_file(f, File.join(fish_config_home, 'functions'))
        end

        fish_completions(reel_config_dir).each do |f|
          link_config_file(f, File.join(fish_config_home, 'completions'))
        end

        @repo_config_files = fish_general_configs(reel_config_dir)
        unless @repo_config_files.empty?
          @repo_config_files.each do |file|
            write_config_file(file, @reel_config_file)
          end
        end
      else
        fail ArgumentError, 'zsh & bash support coming soon!'
      end
    end

    def write_config_file(file_to_source, reel_config_file = @reel_config_file)
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

    def link_config_file(src, dest_dir)
      dst = File.join(dest_dir, File.basename(src))
      FileUtils.ln_s src, dst, force: true
    end

    private

    # TODO: rename shell_type to something else, or make this public?
    def shell_type
      shell_env_var = ENV['SHELL'].downcase
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
  end
end
