require File.expand_path(File.dirname(__FILE__) + '/spec_helper')
require 'reel/bundle'

describe Reel::Bundle do
  before :all do
    @repos = { 'mkwmms/fish-osx' => %w(ql.fish),
               'mkwmms/fish-basic' =>
               %w(clear.fish mkcd.fish port_kill.fish port_top.fish td.fish)
    }
    @reel_config_home = Dir.mktmpdir('test_reel_config_home_')
    # @bundle = FactoryGirl.create :bundle(@reel_config_home, @repos)
    @bundle = Reel::Bundle.new(@reel_config_home, @repos.keys)
  end

  describe '#download' do
    it 'can download real life repos' do
      @bundle.download(@repos.keys)
      @repos.each do |repo, expected_files|
        # TODO: or should I uses checksums or something?
        actual_files = []
        Dir.glob(@reel_config_home + repo + '**/*.{fish,sh,zsh}') do |f|
          actual_files.push File.basename(f)
        end
        actual_files.each { |actual| expect(actual).to eql(expected_files) }
      end
    end
  end

  # TODO: write test for load_configs

  after :all do
    FileUtils.rm_r @reel_config_home, force: true
  end
end
