# encoding: utf-8
require 'rubygems'
require 'bundler'
require_relative 'lib/dotstrap'

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
  gem.name = Dotstrap::NAME
  gem.version = Dotstrap::VERSION
  gem.homepage = Dotstrap::HOMEPAGE
  gem.license = 'MIT'
  # gem.summary = Dotstrap::SUMMARY
  gem.description = Dotstrap::SUMMARY
  gem.email = Dotstrap::EMAIL
  gem.authors = Dotstrap::AUTHOR
  gem.require_paths = ['lib']
  gem.executables = ['ds']
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
