# Module 10 – Version Control (GIT)
**Cognizant Digital Nurture 5.0 | Java FSE**

## All 5 HOLs are MANDATORY

| HOL | Topic | Key Commands | Status |
|---|---|---|---|
| 1 | Setup, Init, Add, Commit, Push | `git init` `git add` `git commit` `git push` | ✅ |
| 2 | .gitignore | `git status` `git check-ignore` | ✅ |
| 3 | Branching & Merging | `git branch` `git checkout` `git merge` | ✅ |
| 4 | Conflict Resolution | `git merge` (conflict) → manual edit → `git add` → `git commit` | ✅ |
| 5 | Cleanup & Push | `git pull` `git push` `git remote prune` | ✅ |

---

## How to Use This Module

Each HOL folder has a `git_commands.sh` script. **Do not run the whole script at once.**
Run each command individually in **Git Bash** so you can observe the output at each step.

```bash
# Open Git Bash, then copy-paste commands one by one from the .sh file
```

---

## Prerequisites (do once before any HOL)

```bash
# 1. Verify Git is installed
git --version

# 2. Create free GitHub account at https://github.com
#    (DO NOT use Cognizant credentials)

# 3. Configure your identity
git config --global user.name  "Your Name"
git config --global user.email "your-email@example.com"

# 4. Configure VS Code as default editor
git config --global core.editor "code --wait"

# 5. Verify configuration
git config --global --list
```

---

## HOL 1 – Git Setup and First Repository

### What you learn
Setting up Git, creating a local repository, tracking files, committing, and pushing to GitHub.

### Key commands and their meaning

| Command | What it does |
|---|---|
| `git init` | Creates a new local repository (adds `.git/` folder) |
| `git status` | Shows working directory and staging area state |
| `git add <file>` | Moves file from working directory → staging area |
| `git add .` | Stages ALL changed/new files |
| `git commit -m "message"` | Saves staged changes to local repository |
| `git remote add origin <url>` | Links local repo to remote GitHub repo |
| `git pull origin main` | Downloads + merges remote changes |
| `git push origin main` | Uploads local commits to remote |

### The 3-Stage Architecture

```
Working Directory  →  Staging Area  →  Local Repository  →  Remote Repository
  (edit files)        git add .        git commit -m         git push
```

### Step-by-step

```bash
mkdir GitDemo && cd GitDemo
git init
echo "Welcome to GitDemo" > welcome.txt
git status           # welcome.txt is "untracked"
git add welcome.txt
git status           # welcome.txt is "staged"
git commit -m "Initial commit: Added welcome.txt"
git remote add origin https://github.com/<username>/GitDemo.git
git push -u origin main
```

---

## HOL 2 – .gitignore

### What you learn
Preventing unwanted files (logs, build artifacts, IDE files) from being committed.

### Key commands

| Command | What it does |
|---|---|
| `git check-ignore -v <file>` | Shows which rule is ignoring the file |
| `git ls-files --ignored --exclude-standard` | Lists all currently ignored files |
| `git add -f <file>` | Force-adds an ignored file |

### Step-by-step

```bash
# Create files to be ignored
echo "Error log" > error.log
mkdir logs && echo "Debug" > logs/debug.log

# Check status (these show as untracked)
git status

# Create .gitignore
cat > .gitignore << 'EOF'
*.log
logs/
EOF

# Now check status again (logs are hidden)
git status   # Only .gitignore appears

# Verify a specific file is ignored
git check-ignore -v error.log
# Output: .gitignore:2:*.log   error.log

# Commit .gitignore
git add .gitignore
git commit -m "Added .gitignore"
git push origin main
```

### Common .gitignore patterns

```gitignore
*.log          # All .log files (any directory)
logs/          # Entire logs folder
target/        # Maven build output
node_modules/  # Node.js dependencies
.DS_Store      # macOS system files
*.class        # Java compiled files
*.orig         # Git merge backup files
```

---

## HOL 3 – Branching and Merging

### What you learn
Creating a feature branch, committing to it, and merging back to main.

### Key commands

| Command | What it does |
|---|---|
| `git branch <name>` | Creates a new branch |
| `git checkout <name>` | Switches to a branch |
| `git checkout -b <name>` | Creates AND switches in one step |
| `git branch` | Lists local branches (`*` = current) |
| `git branch -a` | Lists local + remote branches |
| `git merge <branch>` | Merges named branch into current branch |
| `git branch -d <name>` | Deletes a (merged) branch |
| `git log --oneline --graph --decorate` | Shows visual branch history |

### Step-by-step

```bash
# Verify main is clean
git status

# Create and switch to new branch
git checkout -b GitNewBranch
git branch   # * GitNewBranch

# Make changes on branch
echo "New feature code" > branch_feature.txt
git add .
git commit -m "feat: Added new feature in GitNewBranch"

# Switch back to main
git checkout main
ls   # branch_feature.txt NOT here

# Compare differences
git diff main GitNewBranch

# Merge branch into main
git merge GitNewBranch

# View graph
git log --oneline --graph --decorate
# * (HEAD -> main, GitNewBranch) feat: Added new feature
# * Initial commit

# Delete branch (no longer needed)
git branch -d GitNewBranch
git branch   # Only main remains

git push origin main
```

---

## HOL 4 – Merge Conflict Resolution

### What you learn
What happens when the same file is modified differently on two branches, and how to resolve the conflict.

### How conflicts occur

```
main:       hello.xml → "Hello from Master"
GitWork:    hello.xml → "Hello from Branch"
git merge → CONFLICT! Git cannot decide which to keep.
```

### Conflict markers Git inserts

```xml
<<<<<<< HEAD
<message>Hello from Master Branch</message>
=======
<message>Hello from GitWork Branch</message>
>>>>>>> GitWork
```

**To resolve:** delete ALL three marker lines, keep/edit the content you want, then stage and commit.

### Step-by-step

```bash
# Verify clean state
git status

# Create GitWork branch and add hello.xml
git checkout -b GitWork
echo "<greeting><message>Branch version</message></greeting>" > hello.xml
git add hello.xml
git commit -m "Added hello.xml in GitWork"

# Switch to main and add DIFFERENT hello.xml
git checkout main
echo "<greeting><message>Master version</message></greeting>" > hello.xml
git add hello.xml
git commit -m "Added hello.xml in master"

# View diverged history
git log --oneline --graph --decorate --all

# Try to merge → CONFLICT
git merge GitWork

# See the conflict
git status        # both added: hello.xml
cat hello.xml     # shows <<<<<<< markers

# Resolve: edit hello.xml, remove markers, keep desired content
code hello.xml    # edit manually

# After resolving
git add hello.xml
git commit -m "Resolved merge conflict in hello.xml"

# Clean up
echo "*.orig" >> .gitignore
git add .gitignore
git commit -m "Added *.orig to .gitignore"

git branch -d GitWork
git log --oneline --graph --decorate

git push origin main
```

---

## HOL 5 – Cleanup and Push to Remote

### What you learn
Ensuring local and remote repositories are in sync. Cleaning up merged branches.

### Key commands

| Command | What it does |
|---|---|
| `git pull origin main` | Download + merge latest from remote |
| `git fetch origin` | Download only (no merge) |
| `git log origin/main..main` | Show unpushed local commits |
| `git remote prune origin` | Remove stale remote-tracking branches |
| `git branch -vv` | Show tracking info for each branch |

### Step-by-step

```bash
# Verify clean state
git status
git branch

# Show all branches (local + remote)
git branch -a
git branch -vv   # shows [origin/main: ahead 0, behind 0]

# Pull latest from remote
git pull origin main

# Check for unpushed commits
git log origin/main..main --oneline
# Empty output = all pushed

# Push pending changes
git push origin main

# Verify in sync
git status   # "Your branch is up to date with 'origin/main'"

# Prune deleted remote branches
git remote prune origin

# Final log
git log --oneline --graph --decorate --all
```

---

## Git Command Quick Reference Card

```bash
# ── Repository ────────────────────────────────────
git init                      # Initialize new local repo
git clone <url>               # Clone remote repo locally

# ── Staging & Committing ──────────────────────────
git status                    # Working directory status
git add <file>                # Stage specific file
git add .                     # Stage all changes
git commit -m "message"       # Commit staged changes
git commit --amend            # Modify the last commit

# ── Remote ────────────────────────────────────────
git remote add origin <url>   # Add remote
git remote -v                 # List remotes
git pull origin main          # Fetch + merge
git push origin main          # Upload commits
git push -u origin main       # Upload + set upstream

# ── Branching ─────────────────────────────────────
git branch                    # List local branches
git branch -a                 # List all branches
git branch <name>             # Create branch
git checkout <name>           # Switch to branch
git checkout -b <name>        # Create + switch
git merge <name>              # Merge into current
git branch -d <name>          # Delete merged branch
git branch -D <name>          # Force delete

# ── Logs & Diff ───────────────────────────────────
git log                       # Full commit history
git log --oneline             # Compact history
git log --oneline --graph --decorate --all  # Visual
git diff                      # Unstaged changes
git diff --staged             # Staged changes
git diff main GitNewBranch    # Between branches

# ── .gitignore ────────────────────────────────────
git check-ignore -v <file>    # Which rule ignores this?
git ls-files --ignored --exclude-standard  # All ignored

# ── Undoing ───────────────────────────────────────
git restore <file>            # Discard working changes
git restore --staged <file>   # Unstage a file
git reset HEAD~1              # Undo last commit (keep changes)
git revert <commit-hash>      # Create undo commit (safe)
```

---

## GitHub Repository Setup Checklist

```
✅ Created free GitHub account (not Cognizant credentials)
✅ Created "GitDemo" repository on GitHub
✅ git remote add origin https://github.com/<username>/GitDemo.git
✅ git push -u origin main (first push)
✅ HOL 1: welcome.txt committed and pushed
✅ HOL 2: .gitignore committed and pushed
✅ HOL 3: Merge of GitNewBranch visible in git log --graph
✅ HOL 4: Merge conflict commit visible in git log --graph
✅ HOL 5: Local and remote in sync
```
