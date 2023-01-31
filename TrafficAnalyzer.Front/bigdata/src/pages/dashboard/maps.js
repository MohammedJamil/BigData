import { world_map } from "./world_map.ts";
import * as React from "react";
import * as ReactDOM from "react-dom";
import { MapsComponent, LayersDirective, LayerDirective, Selection, Inject, Legend } from '@syncfusion/ej2-react-maps';
import { registerLicense } from '@syncfusion/ej2-base';
// import * as world_map from './map-data/tooltip-datasource.json';

registerLicense('Mgo+DSMBaFt/QHRqVVhjVFpGaV1BQmFJfFBmRmlbeVRyckU3HVdTRHRcQlxhTX5QdE1mUHlccXM=;Mgo+DSMBPh8sVXJ0S0J+XE9HflRAQmFKYVF2R2BJfFRycl9EZkwgOX1dQl9gSX9RckVgW3dbeXJRRWI=;ORg4AjUWIQA/Gnt2VVhkQlFadVdJX3xLfUx0RWFab116dlNMYlpBNQtUQF1hSn5SdEFjWn1WdX1TQWJe;OTkzOTQzQDMyMzAyZTM0MmUzMEU2UFVmRXViQlFJamE1dHhHbEdRbktTbUw5dFRYUll2ZFVyYk5zTGdDOVU9;OTkzOTQ0QDMyMzAyZTM0MmUzMFhNTWtjZXJEM1ZnbnR4MGdFKzU2eEl0UjBJK1ZrZk9GZnVQUkovVkxlUXM9;NRAiBiAaIQQuGjN/V0Z+WE9EaFxKVmFWfFBpR2NbfE5zfldAal5XVAciSV9jS31Td0dnWXxceHFcQWdeUw==;OTkzOTQ2QDMyMzAyZTM0MmUzME5DTWhxMUQwdlBFcGoxZkJTelZ5N1dIanpSaWdPUjVOQ09xeWMwSllRUFk9;OTkzOTQ3QDMyMzAyZTM0MmUzMFE4WkQ3cHBlcm1XKzBPUFYyK1lpNXRWeXNPdlN3NXBFTFVqU3JLeGNzZ3c9;Mgo+DSMBMAY9C3t2VVhkQlFadVdJX3xLfUx0RWFab116dlNMYlpBNQtUQF1hSn5SdEFjWn1WdX1dQmFe;OTkzOTQ5QDMyMzAyZTM0MmUzMExQeWlqYmhQNDcwZnBacVNDNWhXRVhTVUtseWh0aTVLVFFSdkdHUXV3eE09;OTkzOTUwQDMyMzAyZTM0MmUzMEZ5Z2ZOSWgzZ0dOZXFYMXZFcHB2L3Y3amhnaHJvb21qeW9Yc0lYZnI2SGs9;OTkzOTUxQDMyMzAyZTM0MmUzME5DTWhxMUQwdlBFcGoxZkJTelZ5N1dIanpSaWdPUjVOQ09xeWMwSllRUFk9');

export default function TooltipMaps() {
    return (<MapsComponent legendSettings={{ visible: true }}>
        <Inject services={[Selection, Legend]}/>
            <LayersDirective>
                <LayerDirective shapeData={world_map} selectionSettings={{
        enable: true,
        fill: 'blue',
        border: { color: 'white', width: 2 }
    }} dataSource={[
        { "Country": "China", "Membership": "Permanent" },
        { "Country": "France", "Membership": "Permanent" },
        { "Country": "Russia", "Membership": "Permanent" },
        { "Country": "Kazakhstan", "Membership": "Non-Permanent" },
        { "Country": "Poland", "Membership": "Non-Permanent" },
        { "Country": "Sweden", "Membership": "Non-Permanent" }
    ]} shapePropertyPath="name" shapeDataPath="Country" shapeSettings={{
        colorValuePath: 'Membership',
        colorMapping: [
            { value: 'Permanent', color: '#D84444' },
            { value: 'Non-Permanent', color: '#316DB5' }
        ]
    }}>
                </LayerDirective>
            </LayersDirective>
        </MapsComponent>);
}