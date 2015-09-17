# encoding: utf-8
require 'rubygems'
require 'bundler'
require_relative 'lib/reel'

begin
  Bundler.setup(:default, :development)
rescue Bundler::BundlerError => e
  $stderr.puts e.message
  $stderr.puts 'Run `bundle install` to install missing gems'
  exit e.status_code
end
require 'rake'

require 'jeweler'
Jeweler::Tasks.new do |gem|
  gem.name = Reel::NAME
  gem.version = Reel::VERSION
  gem.homepage = Reel::HOMEPAGE
  gem.license = 'MIT'
  gem.summary = Reel::SUMMARY
  gem.description = Reel::DESCRIPTION
  gem.email = Reel::EMAIL
  gem.authors = Reel::AUTHOR
  gem.require_paths = ['lib']
  gem.executables = ['reel']
  # dependencies defined in Gemfile
end
Jeweler::RubygemsDotOrgTasks.new

require 'rspec/core'
require 'rspec/core/rake_task'
RSpec::Core::RakeTask.new(:spec) do |spec|
  spec.pattern = FileList['spec/**/*_spec.rb']
end

desc 'Code coverage detail'
task :simplecov do
  ENV['COVERAGE'] = 'true'
  Rake::Task['spec'].execute
end

task default: :spec

require 'yard'
YARD::Rake::YardocTask.new
