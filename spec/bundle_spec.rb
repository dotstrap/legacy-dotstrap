require File.expand_path(File.dirname(__FILE__) + '/spec_helper')
require 'dotstrap/bundle'

describe Dotstrap::Bundle do
  before :all do
    @repos = { 'mwilliammyers/fish-osx' => %w(ql.fish),
               'mwilliammyers/fish-basic' =>
               %w(clear.fish mkcd.fish port_kill.fish port_top.fish td.fish)
    }
    @dotstrap_config_dir = Dir.mktmpdir('test_dotstrap_config_dir_')
    # @bundle = FactoryGirl.create :bundle(@config_home, @repos)
    @bundle = Dotstrap::Bundle.new(@dotstrap_config_dir, @repos.keys)
  end

  describe '#download' do
    it 'can download real life repos' do
      @bundle.download(@repos.keys)
      @repos.each do |repo, expected_files|
        # TODO: or should I uses checksums or something?
        actual_files = []
        Dir.glob(@dotstrap_config_dir + repo + '**/*.{fish,sh,zsh}') do |f|
          actual_files.push File.basename(f)
        end
        actual_files.each { |actual| expect(actual).to eql(expected_files) }
      end
    end
  end

  # TODO: write test for load_configs

  after :all do
    FileUtils.rm_r @dotstrap_config_dir, force: true
  end
end
