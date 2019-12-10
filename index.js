'use strict';

import {NativeModules, Platform} from "react-native";
const _CarrierInfo = NativeModules.RNCarrierInfo;

export class CarrierInfo {
	static async allowsVOIP() {
		if (Platform.OS === 'android') return true;
		return await _CarrierInfo.allowsVOIP();
	}

	static async carrierName() {
		return await _CarrierInfo.carrierName();
	}

	static async isoCountryCode() {
		return await _CarrierInfo.isoCountryCode();
	}

	static async mobileCountryCode() {
		return await _CarrierInfo.mobileCountryCode();
	}

	static async mobileNetworkCode() {
		return await _CarrierInfo.mobileNetworkCode();
	}

	static async mobileNetworkOperator() {
		return await _CarrierInfo.mobileNetworkOperator();
	}

	static addCarrierChangeListener(callback) {
		return _CarrierInfo.addCarrierChangeListener(callback);
	}
}
