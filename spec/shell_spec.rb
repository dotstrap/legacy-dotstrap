require File.expand_path(File.dirname(__FILE__) + '/spec_helper')
require 'dotstrap/shell'

describe Dotstrap::Shell do
  before :all do
    # FIXME: decouple test config_files and define in each test
    @config_files = { 'mkwmms/fish-osx' => %w(ql.fish),
                      # 'mkwmms/fish-basic/functions' =>
      # %w(clear.fish mkcd.fish port_kill.fish port_top.fish td.fish)
    }
    @dotstrap_config_home = Dir.mktmpdir('test_dotstrap_config_home_')
    @shell_config_home = Dir.mktmpdir('test_dotstrap_shell_config_home_')
    # @bundle = FactoryGirl.create :bundle(@dotstrap_config_home, @repos)
    @shell = Dotstrap::Shell.new(@dotstrap_config_home, @config_files.keys)

    @config_files.each do |repo, files|
      dir = File.join(@dotstrap_config_home, repo, 'functions')
      FileUtils.mkdir_p dir
      files.each do |f|
        File.write(File.join(dir, f), 'TEST CONFIG')
      end
    end
  end

  describe 'array test' do
    # This will fail if we are not using rspec >= 3.0
    it 'simple match_array' do
      expect([1, 2, 3]).to match_array [2, 3, 1]
    end
  end

  describe '#fish_config_home' do
    # ENV['XDG_CONFIG_HOME']
    it 'gets the correct fish_config_home' do
      expect(@shell.fish_config_home).to eql(File.expand_path('~/.config/fish'))
    end
  end

  describe '#fish_functions' do
    # ENV['XDG_CONFIG_HOME']
    it 'gets the correct fish function files before linking' do
      actual_files = []
      expected_files = []
      @config_files.each do |repo, files|
        dir = File.join(@dotstrap_config_home, repo)
        actual_files.push @shell.fish_functions(dir)
        files.each do |f|
          expected_files.push File.join(dir, 'functions', f)
        end
      end
      # FIXME: worry about the fact that I need to call flatten on arrays?
      expect(actual_files.flatten).to match_array(expected_files.flatten)
    end
  end

  # describe '#configure' do
    # it 'can configure 1 repo' do
      # actual_files = []
      # Dir.glob(@shell_config_home + 'functions/*.fish') do |f|
        # actual_files.push File.basename(f)
      # end
      # expect(actual_files).to match_array(@config_files.values)
    # end
  # end

  after :all do
    [@dotstrap_config_home, @shell_config_home].each do |dir|
      FileUtils.rm_r dir, force: true
    end
  end
end
