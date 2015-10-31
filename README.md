# dotstrap
[![Build Status](https://travis-ci.org/mkwmms/dotstrap.svg)](https://travis-ci.org/mkwmms/dotstrap)

Downloads repositories from GitHub in parallel and symbolically links and/or 
creates a file to be sourced in your 
`~/.zshrc`, `~/.bashrc`, `~/.config/fish/config.fish` or similar 

## how it works

#### fish

 just add `source $XDG_CONFIG_HOME/dotstrap/dotstrap.fish` to your `~/.config/fish/config.fish`

  - `./functions/*.fish` are symbolically linked under `~/.config/fish/functions/`
  - `./completions/*.fish` are symbolically linked under `~/.config/fish/completions/`
  - all other `*.fish` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/dotstrap.fish` 


#### zsh 

just add source `$XDG_CONFIG_HOME/dotstrap/dotstrap.zsh` to your `~/.zshrc`

  - `*.zsh` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/dotstrap.zsh` 
  - `*.sh` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/dotstrap.zsh` 

#### bash

just add source `$XDG_CONFIG_HOME/dotstrap/dotstrap.bash` to your `~/.bash_profile` or similar

  - `*.bash` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/dotstrap.bash` 
  - `*.sh` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/dotstrap.bash` 
  
## install 

`gem install dotstrap` __or__
`git clone https://github.com/mkwmms/dotstrap.git` and then `rake install`

## usage

`ds install <github repo>`

`ds remove <github repo>`

where `<github repo>` is repository slug like 'mkwmms/dotstrap-osx'

you can also install repos from a file like:

`ds install -f FILE`

where `FILE` is a list with the same syntax as `<github repo>`, each seperated
by a new line. (lines starting with `#` are ignored)

`ds list`

run `ds --help` for a full list of commands

## roadmap

This is very much in alpha right now...

- install config files from arbiturary URLs
- list config files when running `ds list <some repo>`
- add a mechanism to define what config files to load and where to put them, possibly
through a YAML (or similar) config file at root of repo or via shell environment variables
- add a mechanism to specify the load order of paths (the order in which the repo config
files are added to dotstrap's config file) so that, for example, `path.{sh,zsh,fish}` 
is loaded first so that it can set up your `$PATH` before anything else

### Copyright

Copyright (c) 2015 William Myers. See LICENSE.txt for further details.

