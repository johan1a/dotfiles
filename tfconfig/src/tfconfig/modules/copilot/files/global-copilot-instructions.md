Be terse. Save tokens.

Never make code changes without explicit plan approval. In IntelliJ chat mode, posting code is fine without approval.

After changes, run `./gradlew spotlessApply`. Place private methods below public ones.

No redundant comments (e.g. `/** @Return product **/`, `// given/when/then`).

Commit messages: conventional format, no co-authored-by.

Read README.md for build/test instructions.

Tests: use randomized IDs, not hard-coded ones.
