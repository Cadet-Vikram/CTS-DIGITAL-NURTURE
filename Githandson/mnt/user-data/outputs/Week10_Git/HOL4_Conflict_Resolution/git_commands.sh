#!/bin/bash
# ============================================================
# HOL 4: Merge Conflict Resolution
# Cognizant DN 5.0 – Version Control (GIT)
# MANDATORY
#
# Task: Create conflicting changes in "hello.xml" on both
#       master and branch "GitWork". Observe conflict markers.
#       Resolve manually and commit.
# ============================================================

echo "============================================================"
echo "  HOL 4 – Merge Conflict Resolution"
echo "============================================================"

cd GitDemo


# ── STEP 1: Verify Master is in Clean State ───────────────────────────
git status
# Expected: "nothing to commit, working tree clean"
git branch
# Expected: * main


# ── STEP 2: Create Branch "GitWork" ───────────────────────────────────
git checkout -b GitWork
git branch
# Expected: * GitWork


# ── STEP 3: Add hello.xml in GitWork Branch ───────────────────────────
cat > hello.xml << 'XML'
<?xml version="1.0" encoding="UTF-8"?>
<greeting>
    <message>Hello from GitWork Branch</message>
    <author>Abhirami S</author>
    <version>Branch Version</version>
</greeting>
XML

cat hello.xml

# Stage and commit in GitWork branch
git add hello.xml
git commit -m "Added hello.xml in GitWork branch"

git status
git log --oneline


# ── STEP 4: Switch to Master and Create DIFFERENT hello.xml ───────────
git checkout main
git branch
# Expected: * main

# Note: hello.xml does NOT exist on master yet (was only in GitWork)
ls -la

# Create hello.xml on master with DIFFERENT content
cat > hello.xml << 'XML'
<?xml version="1.0" encoding="UTF-8"?>
<greeting>
    <message>Hello from Master Branch</message>
    <author>Tech Team</author>
    <version>Master Version</version>
</greeting>
XML

cat hello.xml

# Stage and commit on master
git add hello.xml
git commit -m "Added hello.xml in master branch"

git log --oneline


# ── STEP 5: Observe Both Branches Now Have Different hello.xml ─────────
# View all commits across all branches
git log --oneline --graph --decorate --all

# Output will look like:
# * abc123 (HEAD -> main)  Added hello.xml in master branch
# | * def456 (GitWork)     Added hello.xml in GitWork branch
# |/
# * ghi789                 Initial commit: Added welcome.txt


# ── STEP 6: Check Differences ─────────────────────────────────────────
# See differences between main and GitWork
git diff main GitWork
# Shows lines in GitWork (+) vs main (-)


# ── STEP 7: Attempt to Merge GitWork into Master (Creates Conflict!) ───
git merge GitWork
# Git CANNOT auto-merge because the same lines differ
# Output: CONFLICT (add/add): Merge conflict in hello.xml
#         Automatic merge failed; fix conflicts and then commit the result.

git status
# Output: both added: hello.xml
#         You have unmerged paths.


# ── STEP 8: Open the Conflicted File to See Conflict Markers ──────────
cat hello.xml
# Git inserts conflict markers:
# <<<<<<< HEAD
# (your current branch – main content)
# =======
# (incoming branch – GitWork content)
# >>>>>>> GitWork


# ── STEP 9: Resolve the Conflict Manually ─────────────────────────────
# Edit hello.xml to choose what to keep (remove ALL conflict markers)
# The resolved file should contain the final desired content:

cat > hello.xml << 'XML'
<?xml version="1.0" encoding="UTF-8"?>
<greeting>
    <!-- Conflict resolved: merged both master and GitWork content -->
    <message>Hello from Both Branches – Conflict Resolved</message>
    <author>Abhirami S</author>
    <fromMaster>Master Branch Greeting</fromMaster>
    <fromBranch>GitWork Branch Greeting</fromBranch>
    <version>Merged Version</version>
</greeting>
XML

echo "Conflict in hello.xml has been resolved manually."
cat hello.xml


# ── STEP 10: Stage and Commit the Resolved File ───────────────────────
# Mark the conflict as resolved by staging the file
git add hello.xml

git status
# Expected: Changes to be committed: modified: hello.xml

# Commit the merge resolution
git commit -m "Resolved merge conflict in hello.xml: merged master and GitWork"

git log --oneline --graph --decorate
# Now shows a merge commit connecting both branches


# ── STEP 11: Add Backup File to .gitignore ───────────────────────────
# Git sometimes creates *.orig backup files during conflict resolution
echo "*.orig" >> .gitignore

git add .gitignore
git commit -m "Updated .gitignore: added *.orig backup files"


# ── STEP 12: List Branches and Delete the Merged Branch ───────────────
# List all branches
git branch -a
# Both main and GitWork are visible

# Delete GitWork (safe delete – only works if fully merged)
git branch -d GitWork
# Expected: Deleted branch GitWork (was def456).

# Verify GitWork is gone
git branch
# Expected: only * main

# Final log
git log --oneline --graph --decorate
# Shows clean merge history


# ── STEP 13: Push to Remote ───────────────────────────────────────────
git push origin main

echo ""
echo "============================================================"
echo "  HOL 4 COMPLETE"
echo "  Conflict in hello.xml resolved and committed."
echo "  GitWork branch deleted. Changes pushed to remote."
echo "============================================================"
