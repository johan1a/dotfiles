#!/bin/sh

docker build -f ci/Dockerfile . -t dotfiles-test

docker run --hostname ci dotfiles-test
