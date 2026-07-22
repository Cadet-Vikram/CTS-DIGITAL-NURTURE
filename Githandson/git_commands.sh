#!/bin/bash
# ============================================================
# HOL 1: Git Setup, Configuration, and Basic Repository Operations
# Cognizant DN 5.0 – Version Control (GIT)
# MANDATORY
#
# Topics: git init, git status, git add, git commit, git push, git pull
# Run this script command by command in Git Bash
# ============================================================

echo "============================================================"
echo "  HOL 1 – Git Setup and First Repository"
echo "============================================================"

# ── STEP 1: Verify Git Installation ──────────────────────────────────
# Expected output: git version 2.x.x
git --version


# ── STEP 2: Set up Global Git Configuration ───────────────────────────
# Replace with your actual name and email (do NOT use Cognizant credentials)
git config --global user.name  "Abhirami S"
git config --global user.email "abhiramis@example.com"

# Verify configuration is set correctly
git config --global --list


# ── STEP 3: Configure Default Text Editor ────────────────────────────
# Option A: VS Code (recommended for modern setup)
git config --global core.editor "code --wait"

# Option B: Notepad++ (as specified in the exercise)
# git config --global core.editor "'C:/Program Files/Notepad++/notepad++.exe' -multiInst -notabbar -nosession -noPlugin"

# Verify editor is set
git config --global core.editor

# Open global config in the editor to see all settings (-e flag = editor)
# git config --global -e


# ── STEP 4: Create and Initialize the Local Repository ───────────────
# Create a new project folder named GitDemo
mkdir GitDemo
cd GitDemo

# Initialize a new Git repository (creates .git hidden folder)
git init

# Verify the .git folder was created
ls -la

echo "Git repository initialized in GitDemo/"


# ── STEP 5: Create a File and Add to Repository ───────────────────────
# Create welcome.txt with content
echo "Welcome to GitDemo – Cognizant DN 5.0" > welcome.txt

# Verify the file was created
ls -la

# Verify the content of the file
cat welcome.txt

# Check the status – welcome.txt is in "working directory" (untracked)
git status

# Stage the file (move from working directory to staging area)
git add welcome.txt

# OR stage all changes at once:
# git add .

# Check status again – welcome.txt is now in "staging area" (tracked)
git status


# ── STEP 6: Commit the Changes ────────────────────────────────────────
# Commit with an inline message
git commit -m "Initial commit: Added welcome.txt"

# To open the default editor for multi-line comments instead:
# git commit
# (Type the message in the editor, save, and close)

# Verify the commit was recorded
git status
git log --oneline


# ── STEP 7: Connect to Remote Repository and Push ─────────────────────
# BEFORE RUNNING: Create a free account on GitHub (github.com)
# Create a new repository named "GitDemo" on GitHub
# Copy the repository URL (HTTPS or SSH)

# Add the remote origin (replace URL with your GitHub repo URL)
git remote add origin https://github.com/<your-username>/GitDemo.git

# Verify remote was added
git remote -v

# Pull remote repository to sync (handles any README created on GitHub)
git pull origin main --allow-unrelated-histories
# Note: GitHub now uses 'main' as default branch (not 'master')

# Push local repository to remote
git push -u origin main
# -u sets upstream so future 'git push' works without specifying origin main

echo ""
echo "============================================================"
echo "  HOL 1 COMPLETE"
echo "  Verify by visiting your GitHub repo URL in the browser"
echo "============================================================"
