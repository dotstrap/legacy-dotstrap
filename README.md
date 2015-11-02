# dotstrap
[![Gem Version](https://badge.fury.io/rb/dotstrap.svg)](https://badge.fury.io/rb/dotstrap)
[![Build Status](https://travis-ci.org/mkwmms/dotstrap.svg)](https://travis-ci.org/mkwmms/dotstrap)
[![Code Climate](https://codeclimate.com/github/mkwmms/dotstrap/badges/gpa.svg)](https://codeclimate.com/github/mkwmms/dotstrap)
[![Inline docs](http://inch-ci.org/github/mkwmms/dotstrap.svg?branch=master)](http://inch-ci.org/github/mkwmms/dotstrap)
[![Dependency Status](https://gemnasium.com/mkwmms/dotstrap.svg)](https://gemnasium.com/mkwmms/dotstrap)

Downloads repositories from GitHub in parallel and symbolically links and/or 
creates a file to be sourced in your `~/.zshrc`, `~/.bash_profile`, or `~/.config/fish/config.fish`
  
## get it 

```bash
gem install dotstrap
``` 

__or stay on the bleeding edge:__

```bash
git clone https://github.com/mkwmms/dotstrap.git
```

```bash
rake install
```

## use it

download (or update) and configure `REPOS`:
```bash 
ds install REPO|FILE
```

completely remove any symbolic links, `source` statements from dotstrap's config file, & the repository itself:
```bash
ds uninstall REPO
```

list the URL and install path for all currently installed repositories:
```bash
ds list [REPO]
```

`REPO` is a GitHub repository slug like `mkwmms/dotstrap-osx`

`FILE` is a newline separated list of `REPOS`

## how it works

#### fish

add `source "$XDG_CONFIG_HOME/dotstrap/config.fish"` to your `~/.config/fish/config.fish`

  - `./functions/*.fish` are symbolically linked under `~/.config/fish/functions/`
  - `./completions/*.fish` are symbolically linked under `~/.config/fish/completions/`
  - all other `*.fish` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/config.fish` 


#### zsh 

add `source "$XDG_CONFIG_HOME/dotstrap/dotstrap.zsh"` to your `~/.zshrc`

  - `*.zsh` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/config.zsh` 
  - `*.sh` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/config.zsh` 

#### bash

add `source "$XDG_CONFIG_HOME/dotstrap/config.bash"` to your `~/.bash_profile` or similar

  - `*.bash` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/config.bash` 
  - `*.sh` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/config.bash` 
  
_Note_: if `$XDG_CONFIG_HOME` is not set, it defaults to `~/.config`. Read about the [XDG] base directory spec. 

## roadmap

This is very much in alpha right now. 

Here are some of my thoughts...

- [ ] install config files from arbiturary URLs
- [ ] add a mechanism to specify the load order of paths (the order in which the repo config
files are added to dotstrap's config file) so that, for example, `path.{sh,zsh,fish}` 
is loaded first so that it can set up your `$PATH` before anything else
- [ ] list config files of `REPO` when running `ds list REPO`
- [ ] add a mechanism to define what config files to load and where to put them, possibly
through a YAML (or similar) config file at root of repo or via shell environment variables
- [ ] prompt user to let dotstrap automatically add the correct `source $XDG_CONFIG_HOME/dotstrap/dotstrap.X` statement to the proper shell innitialization file 
- [ ] use YAML (or similar) to allow downloading/loading on conditions (OS, if a program is installed etc.)
- [ ] add a mechanism to determine if a file is simply a fish function (if it is outside of `./functions` and symlink it to `~/.config/fish/functions` instead of `source`ing to take advantage of fish lazy loading functions

[XDG]: http://standards.freedesktop.org/basedir-spec/basedir-spec-latest.html
