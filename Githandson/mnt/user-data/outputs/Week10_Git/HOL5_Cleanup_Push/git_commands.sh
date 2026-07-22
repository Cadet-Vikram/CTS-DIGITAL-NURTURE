#!/bin/bash
# ============================================================
# HOL 5: Cleanup and Push Back to Remote Git
# Cognizant DN 5.0 – Version Control (GIT)
# MANDATORY
#
# Task: Verify master is clean, list branches,
#       pull from remote, push all pending changes,
#       verify changes are reflected in remote repository.
# ============================================================

echo "============================================================"
echo "  HOL 5 – Cleanup and Push to Remote Git"
echo "============================================================"

cd GitDemo


# ── STEP 1: Verify Master is in Clean State ───────────────────────────
git status
# Expected: On branch main
#           nothing to commit, working tree clean

git branch
# Expected: * main (GitWork should already be deleted from HOL 4)


# ── STEP 2: List All Available Branches ───────────────────────────────
# Local branches only
git branch

# All local AND remote branches
git branch -a
# Remote branches appear as: remotes/origin/main

# Show the relationship between local and remote
git branch -vv
# Output: * main abc123 [origin/main] Last commit message


# ── STEP 3: Pull the Latest Changes from Remote ───────────────────────
# Fetch + merge remote changes into local branch
git pull origin main

# What git pull does:
#   1. git fetch origin    → downloads new commits from remote
#   2. git merge origin/main → merges them into local main

# If you only want to download without merging:
# git fetch origin

# After pull, verify local is in sync with remote
git status
# Expected: Your branch is up to date with 'origin/main'

git log --oneline -5
# Shows last 5 commits (should match remote)


# ── STEP 4: Push All Pending Changes from HOL4 ────────────────────────
# Check if there are any unpushed commits
git log origin/main..main --oneline
# If this outputs commits, those are unpushed

# Push to remote
git push origin main

# Verify the push was successful
git status
# Expected: Your branch is up to date with 'origin/main'

git log --oneline --graph --decorate -10
# Shows last 10 commits with branch graph


# ── STEP 5: Final Verification ────────────────────────────────────────
echo ""
echo "── Final Repository State ────────────────────────────────────"

# Show full log with graph
git log --oneline --graph --decorate --all

# Show remote tracking info
git remote -v

# Show difference between local and remote (should be empty = in sync)
git diff origin/main main

echo ""
echo "Open your GitHub repository in browser to verify all commits:"
echo "  https://github.com/<your-username>/GitDemo"
echo ""


# ── BONUS: Useful Cleanup Commands ────────────────────────────────────

# Remove all merged branches (cleanup after HOL 3 and HOL 4)
# git branch --merged | grep -v "\*\|main\|master" | xargs git branch -d

# Prune remote tracking branches that no longer exist on remote
# git remote prune origin

# List remote branches that have been deleted
# git fetch --prune

# Show compact, one-line log of last 10 commits
# git log --oneline -10

# Show file changes in last commit
# git show --stat HEAD

echo ""
echo "============================================================"
echo "  HOL 5 COMPLETE"
echo "  All changes pushed. Repository is clean and in sync."
echo "============================================================"
