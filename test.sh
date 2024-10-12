#!/bin/sh

docker build -f ci/Dockerfile . -t dotfiles-test

docker run --rm --hostname ci dotfiles-test
