git submodule update --init --recursive
git submodule foreach "git fetch"
git submodule foreach "git switch feature/practice6v2"
git submodule foreach "git pull"