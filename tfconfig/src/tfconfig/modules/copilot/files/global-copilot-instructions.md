Be terse. Save tokens.

Never make code changes without explicit plan approval. In IntelliJ chat mode, posting code is fine without approval.

After changes, run `./gradlew spotlessApply`. Place private methods below public ones.

No redundant comments (e.g. `/** @Return product **/`, `// given/when/then`).

Avoid numbered lists in comments.

Commit messages: conventional format, no co-authored-by.

Try to avoid early returns.

Read README.md for build/test instructions.

Tests: use randomized IDs, not hard-coded ones.

Check under ./ first: When user mentions a file, it is likely somewhere in the current dir or its subdirs.
