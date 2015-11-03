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

requires [bundler]

```bash
git clone https://github.com/mkwmms/dotstrap.git
```

```bash
rake install
```

## use it

download (or update) and configure `REPOs`:
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

#### bash

add `source "$XDG_CONFIG_HOME/dotstrap/config.bash"` to your `~/.bash_profile` or similar

  - `*.bash` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/config.bash` 
  - `*.sh` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/config.bash` 

#### zsh 

add `source "$XDG_CONFIG_HOME/dotstrap/dotstrap.zsh"` to your `~/.zshrc`

  - `*.zsh` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/config.zsh` 
  - `*.sh` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/config.zsh` 

#### fish

add `source "$XDG_CONFIG_HOME/dotstrap/config.fish"` to your `~/.config/fish/config.fish`

  - `./functions/*.fish` are symbolically linked under `~/.config/fish/functions/`
  - `./completions/*.fish` are symbolically linked under `~/.config/fish/completions/`
  - all other `*.fish` files' paths are written to: `$XDG_CONFIG_HOME/dotstrap/config.fish` 
  
_Note_: if `$XDG_CONFIG_HOME` is not set, it defaults to `~/.config`. Read about the [XDG] base directory spec. 

## in the wild
The entire purpose of `dotstrap` is to help you make your dotfiles modular while still keeping your prompt speedy. I also wanted to be able to use my [dotfiles] on bash, [zsh] and [fish] (yes I use all three on a daily basis).

There are many repositories out there that will work out of the box with `dotstrap` and many more that would only require minimal tweaking to get them to work. 

Check out the [wiki] for a good list of repos to get you started.

## similar projects
- [antibody]: super fast, great app written in go. It only does ZSH and it is dynamic (it doesn't create a static file that can be sourced from your shell's init file), but man those microseconds are precious when it comes to loading my shell prompt. I still use it sometimes though because it is pretty dang cool.


[dotfiles]: https://github.com/mkwmms/dotfiles
[wiki]: https://github.com/mkwmms/dotstrap/wiki

[XDG]: http://standards.freedesktop.org/basedir-spec/basedir-spec-latest.html

[homebrew]: https://github.com/Homebrew/homebrew
[bundler]: https://github.com/bundler/bundler/
[antibody]: https://github.com/caarlos0/antibody
[zsh]: http://zsh.sourceforge.net
[fish]: http://fishshell.com/
[fasd]: https://github.com/clvv/fasd
