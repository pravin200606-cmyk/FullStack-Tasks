#!/bin/bash
# ============================================================
#   Task 20 - Git Branching, Merging, Rebasing & Conflicts
# ============================================================

set -e   # stop on unexpected errors (we'll handle conflict intentionally)

echo "=============================================="
echo "  Task 20: Git Branching Lab"
echo "=============================================="

# ── 1. SETUP ──────────────────────────────────────────────
echo ""
echo ">>> [1/9] Configuring Git identity..."
git config --global user.name  "Student User"
git config --global user.email "student@example.com"
echo "    Done. Git version: $(git --version)"

# ── 2. CREATE REPOSITORY ──────────────────────────────────
echo ""
echo ">>> [2/9] Creating project repository..."
rm -rf git-lab          # clean slate if re-running
mkdir  git-lab
cd     git-lab

git init
git branch -M main

echo "Hello Main Branch" > file.txt
git add file.txt
git commit -m "Initial commit"
echo "    Repository created on branch 'main'."

# ── 3. FEATURE BRANCH ─────────────────────────────────────
echo ""
echo ">>> [3/9] Creating and working on feature1 branch..."
git checkout -b feature1

echo "Feature 1 changes" >> file.txt
git add file.txt
git commit -m "Feature 1 update"
echo "    Committed changes on 'feature1'."

# ── 4. MERGING ────────────────────────────────────────────
echo ""
echo ">>> [4/9] Merging feature1 into main..."
git checkout main
git merge feature1 --no-edit
echo "    Merge complete."

# ── 5. REBASING ───────────────────────────────────────────
echo ""
echo ">>> [5/9] Creating feature2 branch and rebasing onto main..."
git checkout -b feature2

echo "Feature 2 changes" >> file.txt
git add file.txt
git commit -m "Feature 2 update"

git rebase main
echo "    Rebase complete. feature2 history is now on top of main."

# ── 6. CREATE MERGE CONFLICT ──────────────────────────────
echo ""
echo ">>> [6/9] Intentionally creating a merge conflict..."

# Edit the SAME line in main
git checkout main
echo "Main branch edit" > file.txt
git commit -am "Main update"

# Edit the SAME line in feature1
git checkout feature1
echo "Feature branch edit" > file.txt
git commit -am "Feature update"

# Attempt merge — this WILL conflict; disable set -e for this step
echo "    Triggering conflict merge..."
git checkout main
set +e
git merge feature1 --no-edit 2>&1
MERGE_EXIT=$?
set -e

if [ $MERGE_EXIT -ne 0 ]; then
    echo "    *** Merge conflict detected (expected) ***"
else
    echo "    No conflict detected (may already be resolved)."
fi

# ── 7. RESOLVE MERGE CONFLICT ─────────────────────────────
echo ""
echo ">>> [7/9] Resolving the merge conflict..."

# Write the resolved content (both edits kept, conflict markers removed)
cat > file.txt <<'EOF'
Main branch edit
Feature branch edit
EOF

git add file.txt
git commit -m "Resolved merge conflict"
echo "    Conflict resolved and committed."

# ── 8. REBASE CONFLICT NOTE ───────────────────────────────
echo ""
echo ">>> [8/9] Rebase conflict note (informational)..."
echo "    If a conflict occurs during 'git rebase', resolve the file,"
echo "    run 'git add <file>', then 'git rebase --continue'."

# ── 9. VERIFY RESULT ──────────────────────────────────────
echo ""
echo ">>> [9/9] Final git log:"
echo "----------------------------------------------"
git log --oneline --graph --all
echo "----------------------------------------------"
echo ""
echo "=============================================="
echo "  Task 20 Complete!"
echo "  Repository location: $(pwd)"
echo "=============================================="
