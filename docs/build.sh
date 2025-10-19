#!/bin/bash -xe

docker build -t khulnasoft-sphinx .
rm -rf .build

# extract messages from base document (en) to .pot files
docker run -it -v $(pwd)/..:/docs khulnasoft-sphinx make gettext

# build .po files by new .pot files
docker run -it -v $(pwd)/..:/docs khulnasoft-sphinx sphinx-intl update -p .build/locale -l ja
docker run -it -v $(pwd)/..:/docs khulnasoft-sphinx sphinx-intl update -p .build/locale -l pt_BR

# build html files
docker run -it -v $(pwd)/..:/docs khulnasoft-sphinx make html