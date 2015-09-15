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
      @reel_config_file =
        File.join(@reel_config_home, "reel_config.#{@shell_name}")
    end

    def fish_config_home
      config_home = ENV.fetch('XDG_CONFIG_HOME', File.expand_path('~config'))
      File.join(config_home, 'fish')
    end

    def configure(dir = @repo_path)
      if @shell_name == 'fish'
        configure_fish(dir)
      else
        puts 'zsh & bash support coming soon!'
      end
    end

    def write_config_file(config_files = @repo_config_files,
                          file = @reel_config_file)
      return if config_files.empty?
      File.write(file, config_files.each { |f| "source #{f}\n" })
    end

    private

    # TODO: rename shell_type to something else
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

    def link_fish_config_file(dir_name, src)
      dst = File.join(fish_config_home, dir_name, File.basename(src))
      FileUtils.ln_s src, dst, force: true
    end

    def configure_fish(dir)
      puts "HELLO"
      # FIXME: is there a way to glob {functions, completions} ?
      Dir.glob(dir + 'functions/*.fish') do |src|
        link_fish_config_file('functions', src)
      end

      Dir.glob(dir + 'completions/*.fish') do |src|
        link_fish_config_file('completions', src)
      end

      Dir.glob(dir + '*.fish') { |file| @repo_config_files.push(file) }
      unless @repo_config_files.empty?
        write_config_file(@repo_config_files, @reel_config_file)
      end
    end
  end
end
