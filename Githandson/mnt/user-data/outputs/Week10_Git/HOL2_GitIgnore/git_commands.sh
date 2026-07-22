#!/bin/bash
# ============================================================
# HOL 2: .gitignore – Ignoring Unwanted Files and Folders
# Cognizant DN 5.0 – Version Control (GIT)
# MANDATORY
#
# Task: Create .log files and a log/ folder in the working directory.
#       Update .gitignore so these are excluded from commits.
#       Verify with git status.
# ============================================================

echo "============================================================"
echo "  HOL 2 – .gitignore"
echo "============================================================"

# Navigate into your GitDemo repository (from HOL 1)
cd GitDemo

# ── STEP 1: Create Files That Should Be Ignored ───────────────────────

# Create a .log file (these are generated at runtime – should not be committed)
echo "ERROR 2026-07-22 System error occurred" > error.log
echo "INFO  2026-07-22 Application started"  > app.log

# Create a log folder with a log file inside
mkdir -p logs
echo "DEBUG log entry" > logs/debug.log

# Verify these files exist
ls -la
ls -la logs/

# Check git status BEFORE adding .gitignore
# These files will show as "Untracked files"
git status

echo ""
echo "The .log files and logs/ folder are visible to Git (untracked)."
echo "Now we will add .gitignore to exclude them..."
echo ""


# ── STEP 2: Create the .gitignore File ───────────────────────────────
# The .gitignore file tells Git which files/folders to ignore

cat > .gitignore << 'IGNORE'
# ── Log Files ──────────────────────────────────────────────
# Ignore ALL files with .log extension in any directory
*.log

# ── Log Folders ────────────────────────────────────────────
# Ignore the logs/ folder and everything inside it
logs/

# ── OS and IDE Generated Files (best practices) ────────────
.DS_Store
.idea/
*.class
target/
node_modules/
*.iml
IGNORE

# Verify .gitignore content
cat .gitignore


# ── STEP 3: Verify .gitignore is Working ──────────────────────────────
# Run git status – .log files and logs/ folder should NOT appear
# Only .gitignore itself should appear as a new untracked file
git status

echo ""
echo "Expected: Only '.gitignore' shows as untracked."
echo "The *.log files and logs/ folder should NOT be listed."
echo ""


# ── STEP 4: Add .gitignore to the Repository ──────────────────────────
# Stage .gitignore
git add .gitignore

# Verify it's staged
git status

# Commit .gitignore
git commit -m "Added .gitignore: ignores *.log files and logs/ folder"

# Push to remote
git push origin main

echo ""
echo "============================================================"
echo "  HOL 2 COMPLETE"
echo "  .gitignore committed. Log files will now be ignored forever."
echo "============================================================"


# ── BONUS: Useful .gitignore commands ─────────────────────────────────

# Check if a specific file is being ignored:
# git check-ignore -v error.log
# Output shows which .gitignore rule is matching

# List all currently ignored files:
# git ls-files --ignored --exclude-standard

# Force-add a file that is currently ignored (use sparingly):
# git add -f error.log
