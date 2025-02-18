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

package net.fabricmc.fabric.impl.qsl.crash;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.crash.api.CrashReportEvents;

public class QuiltCrashInfoCompatMod implements ModInitializer {
	@Override
	public void onInitialize(ModContainer mod) {
		CrashReportEvents.SYSTEM_DETAILS.register(details -> {
			details.addSection("Quilted Fabric API", "!! WARNING !! This instance is using Fabric API modules "
					+ "re-implemented by QSL. If the issue comes from Quilted Fabric API, "
					+ "DO NOT report to Fabric; report them to Quilt instead!");
		});
	}
}
