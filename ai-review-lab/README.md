# AI review stress-test module

This directory contains 55 synthetic Java classes with intentionally low-quality code.

It is used only to exercise SketchPR with a large pull request:

- 56 changed files (55 Java classes plus this README)
- repeated defect patterns: null dereferences, off-by-one loops, swallowed exceptions,
  unsafe SQL concatenation, race conditions, wrong comparisons, wrong indexes,
  missing returns, mutable static state, and incorrect null checks
- no production consumers and no build-module registration

SketchPR limits a single AI review to the first 20 Java files and 60,000 context
characters, so this PR also verifies that the tool behaves correctly when the
change set is larger than the configured context budget.
