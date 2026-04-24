package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    public GameState newGame() {
        return new GameState();
    }

    public void startGame(GameState state, String playerName) {
        String normalizedName = playerName == null ? "" : playerName.trim();
        if (normalizedName.isEmpty()) {
            normalizedName = "Student";
        }
        state.setPlayerName(normalizedName);
        state.setStep(GameStep.FRESHMAN_1);
    }

    public GameView buildView(GameState state) {
        return switch (state.getStep()) {
            case WELCOME -> new GameView(
                    "Four Years Later",
                    "College RPG",
                    "Four Years Later",
                    List.of(
                            "Welcome to Four Years Later.",
                            "Enter your name to begin your college journey.",
                            "This game simulates four years of college as AI is rapidly changing the job market.",
                            "Most entry-level jobs are going to be replaced by AI sooner than you imagine.",
                            "Your choices will shape your skills, your future, and your ending.",
                            "Be careful... Every decision matters."
                    ),
                    List.of(),
                    true,
                    false,
                    state
            );
            case FRESHMAN_1 -> question(state, "Freshman Year",
                    "Your first-year writing professor assigns a 1000-word review about yourself.",
                    List.of("But you do not know what to say about yourself yet.", "How do you complete the assignment?"),
                    List.of(
                            new DecisionOption(1, "Do it on your own"),
                            new DecisionOption(2, "Brainstorm with AI, then write it yourself"),
                            new DecisionOption(3, "Let AI write the whole thing")
                    ));
            case FRESHMAN_2 -> question(state, "Winter Break", "Freshman winter break arrives.",
                    List.of("How do you want to spend your free time?"),
                    List.of(
                            new DecisionOption(1, "Relax and sleep all day"),
                            new DecisionOption(2, "Take a seminar on AI ethics"),
                            new DecisionOption(3, "Enroll in an online cybersecurity course")
                    ));
            case FRESHMAN_3 -> question(state, "Final Paper", "Your research paper is due and time is short.",
                    List.of("How do you approach it?"),
                    List.of(
                            new DecisionOption(1, "Write it on your own"),
                            new DecisionOption(2, "Use AI for an outline, then write it yourself"),
                            new DecisionOption(3, "Ask AI to generate the whole paper")
                    ));
                case FRESHMAN_4 -> freshmanSummerView(state);
                case SOPHOMORE_1 -> question(state, "Sophomore Year", "Public speaking class wants a 15-minute talk about yourself.",
                        List.of("What do you do?"),
                        List.of(
                                new DecisionOption(1, "Ask family for real stories"),
                                new DecisionOption(2, "Use ChatGPT to brainstorm ideas"),
                                new DecisionOption(3, "Let ChatGPT write the whole speech")
                        ));
                case SOPHOMORE_2 -> question(state, "Research Opportunity", "A professor invites you to join research on AI in the workplace.",
                        List.of("Do you join?"),
                        List.of(
                                new DecisionOption(1, "Yes, join the project"),
                                new DecisionOption(2, "No, I do not have time")
                        ));

                case SOPHOMORE_3 -> question(state, "Choose a Major", "It is time to declare your major.",
                        List.of("Which path do you choose?"),
                        List.of(
                                new DecisionOption(1, "Business and Marketing"),
                                new DecisionOption(2, "Software Engineering"),
                                new DecisionOption(3, "Computer Science")
                        ));
                case SOPHOMORE_4 -> sophomoreSummerView(state);
                case JUNIOR_1 -> question(state, "Junior Year", "You are close to finishing your major requirements.",
                        List.of("Do you want a minor?"),
                        List.of(
                                new DecisionOption(1, "Communications"),
                                new DecisionOption(2, "Data Science and Analytics"),
                                new DecisionOption(3, "Psychology"),
                                new DecisionOption(4, "No minor")
                        ));
            case JUNIOR_2 -> question(state, "Resume Building", "You need a resume before graduation.",
                    List.of("How do you build it?"),
                    List.of(
                            new DecisionOption(1, "Draft it and let AI polish it"),
                            new DecisionOption(2, "Write it first, then ask AI for improvements"),
                            new DecisionOption(3, "Go to the university career center")
                    ));
            case JUNIOR_3 -> question(state, "Physics Struggle", "Physics feels harder than expected.",
                    List.of("What action do you take?"),
                    List.of(
                            new DecisionOption(1, "Work hard and learn deeply"),
                            new DecisionOption(2, "Use AI as a learning tool"),
                            new DecisionOption(3, "Do the bare minimum to pass")
                    ));
            case JUNIOR_4 -> question(state, "Group Project", "Your teammates are not contributing.",
                    List.of("What do you do?"),
                    List.of(
                            new DecisionOption(1, "Carry the team yourself"),
                            new DecisionOption(2, "Confront them directly"),
                            new DecisionOption(3, "Do the bare minimum")
                    ));
            case SENIOR_1 -> question(state, "Senior Year", "There is a campus job fair this weekend.",
                    List.of("Do you go networking?"),
                    List.of(
                            new DecisionOption(1, "Yes, go"),
                            new DecisionOption(2, "No, stay home")
                    ));
            case SENIOR_2 -> question(state, "Last Winter Break", "It is your final winter break as a college student.",
                    List.of("What do you do?"),
                    List.of(
                            new DecisionOption(1, "Take a break at home"),
                            new DecisionOption(2, "Revise your resume"),
                            new DecisionOption(3, "Study AI's effect on the job market")
                    ));
            case SENIOR_3 -> question(state, "Graduation Is Close", "Do you start applying for full-time jobs now?",
                    List.of("Choose your move."),
                    List.of(
                            new DecisionOption(1, "Yes, send lots of applications"),
                            new DecisionOption(2, "No, wait")
                    ));
            case GRADUATION -> new GameView(
                    "Four Years Later",
                    "Graduation",
                    "You made it to graduation",
                    List.of(
                            "UNIVERSITY OF MINNESOTA",
                            state.getPlayerName(),
                            "The faculty confer upon you the degree of Bachelor of Science in " + state.getMajor() + ".",
                            "Your college journey is complete."
                    ),
                    List.of(new DecisionOption(1, "See final results")),
                    true,
                    false,
                    state
            );
            case ENDING -> new GameView(
                    "Four Years Later",
                    "Final Results",
                    ensureEnding(state).getEndingTitle(),
                    state.getEndingLines(),
                    List.of(
                            new DecisionOption(1, "Reflect on your journey"),
                            new DecisionOption(2, "Finish the game")
                    ),
                    true,
                    true,
                    state
            );
            case REFLECTION -> new GameView(
                    "Four Years Later",
                    "Reflection",
                    "Final Reflection",
                    List.of(
                            "Throughout your college journey, every decision shaped your outcome.",
                            "How you used AI, managed your time, and built your skills determined where you ended up.",
                            "AI is both a powerful tool and a real risk for college graduates.",
                            "Many entry-level roles are being automated or redefined.",
                            "Keep improving your technical, creative, and problem-solving abilities."
                    ),
                    List.of(new DecisionOption(1, "Play again")),
                    true,
                    true,
                    state
            );
            case COMPLETE -> new GameView(
                    "Four Years Later",
                    "Complete",
                    "Journey complete",
                    List.of("You can restart and try for a different ending."),
                    List.of(new DecisionOption(1, "Play again")),
                    true,
                    true,
                    state
            );
        };
    }

    public void applyChoice(GameState state, int choice) {
        switch (state.getStep()) {
            case WELCOME -> state.setStep(GameStep.FRESHMAN_1);
            case FRESHMAN_1 -> {
                if (choice == 1) {
                    addStats(state, 5, 5, 0);
                } else if (choice == 2) {
                    addStats(state, 0, -2, 2);
                } else if (choice == 3) {
                    addStats(state, -5, -10, 0);
                }
                state.setStep(GameStep.FRESHMAN_2);
            }
            case FRESHMAN_2 -> {
                if (choice == 1) {
                    addStats(state, -2, 0, 0);
                } else if (choice == 2) {
                    addStats(state, 5, 0, 10);
                    state.getSkills().add("AI Ethics");
                } else if (choice == 3) {
                    addStats(state, 7, 0, 12);
                    state.getSkills().add("Cybersecurity");
                }
                state.setStep(GameStep.FRESHMAN_3);
            }
            case FRESHMAN_3 -> {
                if (choice == 1) {
                    addStats(state, 8, 6, 0);
                } else if (choice == 2) {
                    addStats(state, 2, -3, 1);
                } else if (choice == 3) {
                    addStats(state, -8, -8, 0);
                }
                state.setStep(GameStep.FRESHMAN_4);
            }
            case FRESHMAN_4 -> {
                if (choice == 1) {
                    addStats(state, -3, -1, 0);
                } else if (choice == 2) {
                    addStats(state, 10, 0, 10);
                    state.getSkills().add("Cybersecurity");
                } else if (choice == 3) {
                    addStats(state, 10, 0, 10);
                    state.getSkills().add("AI Ethics");
                }
                state.setStep(GameStep.SOPHOMORE_1);
            }
            case SOPHOMORE_1 -> {
                if (choice == 1) {
                    addStats(state, 2, 3, 0);
                } else if (choice == 2) {
                    addStats(state, 0, -2, 2);
                } else if (choice == 3) {
                    addStats(state, -5, -8, 0);
                }
                state.setStep(GameStep.SOPHOMORE_2);
            }
            case SOPHOMORE_2 -> {
                if (choice == 1) {
                    addStats(state, 10, 10, 25);
                }
                state.setStep(GameStep.SOPHOMORE_3);
            }
            case SOPHOMORE_3 -> {
                if (choice == 1) {
                    state.setMajor("Business and Marketing");
                } else if (choice == 2) {
                    state.setMajor("Software Engineering");
                } else if (choice == 3) {
                    state.setMajor("Computer Science");
                }
                state.setStep(GameStep.SOPHOMORE_4);
            }
            case SOPHOMORE_4 -> {
                applySophomoreSummer(choice, state);
                state.setStep(GameStep.JUNIOR_1);
            }
            case JUNIOR_1 -> {
                if (choice == 1) {
                    addStats(state, 10, 7, 2);
                } else if (choice == 2) {
                    addStats(state, 20, 10, 25);
                    state.getSkills().add("Data Analytics");
                } else if (choice == 3) {
                    addStats(state, 20, 10, 5);
                } else if (choice == 4) {
                    addStats(state, 5, 1, 1);
                }
                state.setStep(GameStep.JUNIOR_2);
            }
            case JUNIOR_2 -> {
                if (choice == 1) {
                    addStats(state, -20, -30, -30);
                    state.setResume(1);
                } else if (choice == 2) {
                    addStats(state, 10, 15, 10);
                    state.setResume(2);
                } else if (choice == 3) {
                    addStats(state, 15, 10, 0);
                    state.setResume(3);
                }
                state.setStep(GameStep.JUNIOR_3);
            }
            case JUNIOR_3 -> {
                if (choice == 1) {
                    addStats(state, 10, 0, 0);
                } else if (choice == 2) {
                    addStats(state, 15, 0, 10);
                } else if (choice == 3) {
                    addStats(state, -5, 0, 0);
                }
                state.setStep(GameStep.JUNIOR_4);
            }
            case JUNIOR_4 -> {
                if (choice == 1) {
                    addStats(state, 12, 5, 0);
                } else if (choice == 2) {
                    addStats(state, 10, 0, 0);
                    state.getSkills().add("Leadership");
                } else if (choice == 3) {
                    addStats(state, -8, -5, 0);
                }
                state.setStep(GameStep.SENIOR_1);
            }
            case SENIOR_1 -> {
                if (choice == 1) {
                    addStats(state, 2, 0, 0);
                    state.getSkills().add("Networking");
                } else if (choice == 2) {
                    addStats(state, -2, 0, 0);
                }
                state.setStep(GameStep.SENIOR_2);
            }
            case SENIOR_2 -> {
                if (choice == 1) {
                    addStats(state, -5, -2, 0);
                } else if (choice == 2) {
                    state.setResume(2);
                } else if (choice == 3) {
                    addStats(state, 10, 0, 20);
                }
                state.setStep(GameStep.SENIOR_3);
            }
            case SENIOR_3 -> {
                if (choice == 1) {
                    addStats(state, 5, 0, 0);
                } else if (choice == 2) {
                    addStats(state, -1, 0, 0);
                }
                state.setStep(GameStep.GRADUATION);
            }
            case GRADUATION -> state.setStep(GameStep.ENDING);
            case ENDING -> state.setStep(choice == 1 ? GameStep.REFLECTION : GameStep.COMPLETE);
            case REFLECTION, COMPLETE -> reset(state);
        }
    }

    private GameView freshmanSummerView(GameState state) {
        String firstSkill = state.getSkills().isEmpty() ? "none" : state.getSkills().getFirst();
        List<DecisionOption> options = switch (firstSkill) {
            case "AI Ethics" -> List.of(
                    new DecisionOption(1, "Hang out and do nothing else"),
                    new DecisionOption(2, "Attend a cybersecurity conference")
            );
            case "Cybersecurity" -> List.of(
                    new DecisionOption(1, "Hang out and do nothing else"),
                    new DecisionOption(3, "Take an AI Ethics course")
            );
            default -> List.of(
                    new DecisionOption(1, "Hang out and do nothing else"),
                    new DecisionOption(2, "Learn cybersecurity"),
                    new DecisionOption(3, "Learn AI Ethics")
            );
        };
        return question(state, "Summer Break", "Freshman summer choices",
                List.of("It is summer break. What is your plan?"),
                options);
    }

    private GameView sophomoreSummerView(GameState state) {
        return switch (state.getMajor()) {
            case "Business and Marketing" -> question(state, "Summer Plans", "Business and Marketing summer",
                    List.of("Choose how to spend your summer."),
                    List.of(
                            new DecisionOption(1, "3M marketing internship"),
                            new DecisionOption(2, "Workflow automation training"),
                            new DecisionOption(3, "Do both")
                    ));
            case "Software Engineering" -> question(state, "Summer Plans", "Software Engineering summer",
                    List.of("Choose how to spend your summer."),
                    List.of(
                            new DecisionOption(4, "Amazon assistant software engineer internship"),
                            new DecisionOption(5, "Prompt engineering class"),
                            new DecisionOption(6, "Do both")
                    ));
            default -> question(state, "Summer Plans", "Computer Science summer",
                    List.of("Choose how to spend your summer."),
                    List.of(
                            new DecisionOption(7, "Microsoft intern programmer role"),
                            new DecisionOption(8, "AI machine learning course"),
                            new DecisionOption(9, "Do both")
                    ));
        };
    }

    private void applySophomoreSummer(int choice, GameState state) {
        if (choice == 1 || choice == 4 || choice == 7) {
            addStats(state, 15, 12, 7);
        } else if (choice == 2) {
            addStats(state, 15, 12, 10);
            state.getSkills().add("Workflow Automation");
        } else if (choice == 3) {
            addStats(state, 30, 20, 17);
            state.getSkills().add("Workflow Automation");
        } else if (choice == 5) {
            addStats(state, 15, 12, 10);
            state.getSkills().add("Prompt Engineering");
        } else if (choice == 6) {
            addStats(state, 30, 20, 17);
            state.getSkills().add("Prompt Engineering");
        } else if (choice == 8) {
            addStats(state, 15, 12, 10);
            state.getSkills().add("AI Machine Learning");
        } else if (choice == 9) {
            addStats(state, 30, 20, 17);
            state.getSkills().add("AI Machine Learning");
        }
    }

    private GameState ensureEnding(GameState state) {
        if (state.getEndingTitle() != null) {
            return state;
        }
        if (state.getAiLiteracy() >= 60 && state.getIntelligence() >= 70
                && (state.getResume() == 2 || state.getResume() == 3)
                && state.getSkills().size() >= 2) {
            state.setEndingTitle("AI-Driven Success");
            state.getEndingLines().addAll(List.of(
                    "Profile Summary:",
                    "You treated AI as a tool to enhance your thinking, not replace it. You balanced independent effort with smart use of AI, allowing you to learn efficiently while still building strong foundational skills. Over time, you developed a clear direction and invested consistently in skills that are relevant in an AI-driven world.",
                    "",
                    "Outcome:",
                    "After graduation, you stand out in a competitive job market. You secure a strong role in a high-demand field such as technology, business strategy, or data-related work. You likely live in a city with growing opportunities and continue advancing quickly in your career. Your ability to adapt keeps you ahead as industries evolve.",
                    "",
                    "Suggestions:",
                    "Continue building on this momentum. Focus on combining technical knowledge with creativity and critical thinking. As AI continues to evolve, your advantage will come from how well you can guide, interpret, and apply it. Keep learning, stay adaptable, and take on challenges that push your limits."
            ));
        } else if (state.getCreativity() >= 60 && state.getAiLiteracy() >= 30) {
            state.setEndingTitle("Balanced but Uncertain");
            state.getEndingLines().addAll(List.of(
                    "Profile Summary:",
                    "You made a mix of good and inconsistent decisions. You used AI at times, but not always in a way that maximized learning. While you put in effort, your approach lacked a clear strategy, and your skill development was somewhat scattered.",
                    "",
                    "Outcome:",
                    "After graduation, you enter the job market with moderate readiness. You may find a job, but it might not fully match your interests or potential. You are in a transitional stage, where your future direction is still being shaped. Progress is possible, but it will require more focus and intention.",
                    "",
                    "Suggestions:",
                    "Now is the time to become more deliberate. Identify a specific area to grow in and commit to it. Use AI as a tool to deepen your understanding, not just to complete tasks. Build concrete skills through projects, internships, or certifications. With consistent effort, you can significantly improve your trajectory."
            ));
        } else if (state.getIntelligence() >= 30 && state.getAiLiteracy() >= 30) {
            state.setEndingTitle("Balanced but Uncertain");
            state.getEndingLines().addAll(List.of(
                    "Profile Summary:",
                    "You made a mix of good and inconsistent decisions. You used AI at times, but not always in a way that maximized learning. While you put in effort, your approach lacked a clear strategy, and your skill development was somewhat scattered.",
                    "",
                    "Outcome:",
                    "After graduation, you enter the job market with moderate readiness. You may find a job, but it might not fully match your interests or potential. You are in a transitional stage, where your future direction is still being shaped. Progress is possible, but it will require more focus and intention.",
                    "",
                    "Suggestions:",
                    "Now is the time to become more deliberate. Identify a specific area to grow in and commit to it. Use AI as a tool to deepen your understanding, not just to complete tasks. Build concrete skills through projects, internships, or certifications. With consistent effort, you can significantly improve your trajectory."
            ));
        } else {
            state.setEndingTitle("Left Behind by AI");
            state.getEndingLines().addAll(List.of(
                    "Profile Summary:",
                    "You relied too heavily on shortcuts or avoided putting in consistent effort. Your use of AI did not translate into real learning or skill development. As a result, your abilities have not kept pace with the changing demands of the job market.",
                    "",
                    "Outcome:",
                    "After graduation, you face significant challenges finding stable employment. Many entry-level roles have been automated or now require stronger, more adaptable skill sets. You may find yourself taking temporary or unrelated jobs while trying to figure out your next steps.",
                    "",
                    "Suggestions:",
                    "This is a turning point. Focus on rebuilding your foundation by committing to genuine learning. Start developing practical, in-demand skills and learn how to use AI as a support tool rather than a replacement. Consider structured learning paths, certifications, or guided programs. Progress may take time, but consistent effort can still change your outcome."
            ));
        }
        return state;
    }

    private GameView question(GameState state, String label, String heading, List<String> lines, List<DecisionOption> options) {
        return new GameView("Four Years Later", label, heading, lines, options, true, false, state);
    }

    private void addStats(GameState state, int intelligence, int creativity, int aiLiteracy) {
        state.setIntelligence(state.getIntelligence() + intelligence);
        state.setCreativity(state.getCreativity() + creativity);
        state.setAiLiteracy(state.getAiLiteracy() + aiLiteracy);
    }

    private void reset(GameState state) {
        state.setPlayerName("");
        state.setIntelligence(10);
        state.setCreativity(10);
        state.setAiLiteracy(0);
        state.setResume(0);
        state.setMajor("Undeclared");
        state.setStep(GameStep.WELCOME);
        state.getSkills().clear();
        state.setEndingTitle(null);
        state.getEndingLines().clear();
    }
}
