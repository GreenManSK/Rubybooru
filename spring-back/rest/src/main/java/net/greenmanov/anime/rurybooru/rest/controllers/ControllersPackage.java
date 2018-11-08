package net.greenmanov.anime.rurybooru.rest.controllers;

/**
 * This no-op marker class ony exists to provide package path for Spring dependency injection.
 *
 * @see org.springframework.context.annotation.ComponentScan#basePackageClasses
 */
public final class ControllersPackage {
    private ControllersPackage() {
        throw new AssertionError("This is not a class you are looking for.");
    }
}
