#!/bin/bash
# ============================================================
# HOL 3: Branching and Merging
# Cognizant DN 5.0 – Version Control (GIT)
# MANDATORY
#
# Task: Create branch "GitNewBranch", add files, commit,
#       merge back to master, delete branch.
#       Observe visual diff and git log --graph.
# ============================================================

echo "============================================================"
echo "  HOL 3 – Branching and Merging"
echo "============================================================"

cd GitDemo


# ── STEP 1: Verify Master is Clean ────────────────────────────────────
git status
# Expected: "nothing to commit, working tree clean"

# Show current branch
git branch
# Expected: * main (or * master)

echo "Current branch should be main/master with clean state."


# ── STEP 2: Create a New Branch ───────────────────────────────────────
# Creates a new branch called "GitNewBranch"
git branch GitNewBranch

# List all local branches
# The * indicates the CURRENT branch (still on main)
git branch

# List all local AND remote branches
git branch -a


# ── STEP 3: Switch to the New Branch ──────────────────────────────────
git checkout GitNewBranch
# Modern alternative (Git 2.23+):
# git switch GitNewBranch

# Shortcut to create AND switch in one command:
# git checkout -b GitNewBranch

# Verify we are now on GitNewBranch
git branch
# Expected: * GitNewBranch


# ── STEP 4: Add Files and Content in the Branch ───────────────────────
# Create a new file in this branch
echo "This file was created in GitNewBranch" > branch_feature.txt
echo "Feature: User Login Module"             >> branch_feature.txt
echo "Developer: Abhirami S"                  >> branch_feature.txt

# Also modify an existing file in the branch
echo ""                                         >> welcome.txt
echo "Updated in GitNewBranch – Login feature" >> welcome.txt

# Check status
git status


# ── STEP 5: Stage and Commit Changes in the Branch ────────────────────
git add .
git commit -m "feat: Added login feature in GitNewBranch"

# Verify the commit
git status
git log --oneline
# Only shows commits on GitNewBranch


# ── STEP 6: Switch Back to Master ─────────────────────────────────────
git checkout main
# OR: git switch main

# Verify: branch_feature.txt should NOT exist here
ls -la
# welcome.txt should be back to the original (branch changes not visible)
cat welcome.txt

git branch
# Expected: * main


# ── STEP 7: Compare Differences Between Master and Branch ─────────────
# Command-line diff: shows all differences between main and GitNewBranch
git diff main GitNewBranch

# Log showing diverged history
git log --oneline --all
# Shows commits on both branches


# ── STEP 8: Merge the Branch into Master ──────────────────────────────
# Merge GitNewBranch → main
git merge GitNewBranch
# This is a "fast-forward" merge if main has no new commits since branching

# If the merge is successful (no conflicts):
# All changes from GitNewBranch are now in main
ls -la
cat welcome.txt   # Should now include the branch updates

# Observe the git log with graph visualization
git log --oneline --graph --decorate
# Visual output shows the branch and merge point:
# * (HEAD -> main, GitNewBranch) feat: Added login feature
# * Initial commit: Added welcome.txt


# ── STEP 9: Delete the Branch ─────────────────────────────────────────
# After merging, the branch is no longer needed
git branch -d GitNewBranch
# -d = safe delete (only deletes if already merged)
# -D = force delete (even if not merged)

# Verify the branch is deleted
git branch
# Expected: only main is listed

git log --oneline --graph --decorate
# The merge is visible in the graph


# ── STEP 10: Push Merged Changes to Remote ────────────────────────────
git push origin main

echo ""
echo "============================================================"
echo "  HOL 3 COMPLETE"
echo "  Branch created, changes committed, merged, and deleted."
echo "  Run: git log --oneline --graph --decorate"
echo "============================================================"
