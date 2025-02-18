/*
 * Copyright 2016, 2017, 2018, 2019 FabricMC
 * Copyright 2022 QuiltMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.api.biome.v1;

import net.minecraft.world.biome.Biome;

/**
 * Allows {@link Biome} properties to be modified.
 *
 * <p><b>Experimental feature</b>, may be removed or changed without further notice.
 */
@Deprecated
public interface BiomeModificationContext extends org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext {
	/**
	 * Returns the modification context for the biomes weather properties.
	 */
	default WeatherContext getWeather() {
		return (WeatherContext) ((org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext) this).getWeather();
	}

	/**
	 * Returns the modification context for the biomes effects.
	 */
	default EffectsContext getEffects() {
		return (EffectsContext) ((org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext) this).getEffects();
	}

	/**
	 * Returns the modification context for the biomes generation settings.
	 */
	default GenerationSettingsContext getGenerationSettings() {
		return (GenerationSettingsContext) ((org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext) this).getGenerationSettings();
	}

	/**
	 * Returns the modification context for the biomes spawn settings.
	 */
	default SpawnSettingsContext getSpawnSettings() {
		return (SpawnSettingsContext) ((org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext) this).getSpawnSettings();
	}

	interface WeatherContext extends org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext.WeatherContext { }

	interface EffectsContext extends org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext.EffectsContext { }

	interface GenerationSettingsContext extends org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext.GenerationSettingsContext { }

	interface SpawnSettingsContext extends org.quiltmc.qsl.worldgen.biome.api.BiomeModificationContext.SpawnSettingsContext { }
}
