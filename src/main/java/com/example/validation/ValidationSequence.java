package com.example.validation;

import javax.validation.GroupSequence;

@GroupSequence({PrimaryValidation.class, SecondaryValidation.class})
public interface ValidationSequence {}
