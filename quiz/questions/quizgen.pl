#!/usr/bin/perl

#
# Usage is: $0 chapnum qcount
#
if ($#ARGV != 1) {
  die "Usage is: $0 chNum quCount\n";
}

$chNum = $ARGV[0];
$quCount = $ARGV[1];

print "###\n# Chapter $chNum\n###\n";

printf "%X0-ti Chapter %d Title\n", $chNum, $chNum;
printf "%X0-in Chapter %d Intro\n\n", $chNum, $chNum;

for ($q = 1; $q <= $quCount; $q++) {
  printf "%X%X-tx Question %d text\n", $chNum, $q, $q;
  printf "%X%X-se (Select one)\n", $chNum, $q;
  for ($a = 65; $a <= 68; $a++) {
    printf "%X%X%ctw Answer %c\n", $chNum, $q, $a, $a;
    printf "%X%X%cex Explanation %c\n", $chNum, $q, $a, $a;
  }
  printf "\n";
}

