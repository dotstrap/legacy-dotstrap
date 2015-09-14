# encoding: utf-8
require 'rubygems'
require 'bundler'
require_relative 'lib/reel/version'

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
  gem.name = 'reel'
  gem.version = Reel::VERSION
  gem.homepage = 'http://github.com/mkwmms/reel'
  gem.license = 'MIT'
  gem.summary = %(Download & install shell config files from GitHub repos)
  gem.description = %(Downloads repositories from GitHub in parallel and 
                      symbolically links and/or creates a static source page
                      according to your shell \(fish, zsh, bash\))
  gem.email = 'mkwmms@icloud.com'
  gem.authors = ['William Myers']
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
