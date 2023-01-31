import * as React from 'react';
import { enableRipple } from '@syncfusion/ej2-base';
import { registerLicense } from '@syncfusion/ej2-base';
registerLicense('Mgo+DSMBaFt/QHRqVVhjVFpGaV1BQmFJfFBmRmlbeVRyckU3HVdTRHRcQlxhTX5QdE1mUHlccXM=;Mgo+DSMBPh8sVXJ0S0J+XE9HflRAQmFKYVF2R2BJfFRycl9EZkwgOX1dQl9gSX9RckVgW3dbeXJRRWI=;ORg4AjUWIQA/Gnt2VVhkQlFadVdJX3xLfUx0RWFab116dlNMYlpBNQtUQF1hSn5SdEFjWn1WdX1TQWJe;OTkzOTQzQDMyMzAyZTM0MmUzMEU2UFVmRXViQlFJamE1dHhHbEdRbktTbUw5dFRYUll2ZFVyYk5zTGdDOVU9;OTkzOTQ0QDMyMzAyZTM0MmUzMFhNTWtjZXJEM1ZnbnR4MGdFKzU2eEl0UjBJK1ZrZk9GZnVQUkovVkxlUXM9;NRAiBiAaIQQuGjN/V0Z+WE9EaFxKVmFWfFBpR2NbfE5zfldAal5XVAciSV9jS31Td0dnWXxceHFcQWdeUw==;OTkzOTQ2QDMyMzAyZTM0MmUzME5DTWhxMUQwdlBFcGoxZkJTelZ5N1dIanpSaWdPUjVOQ09xeWMwSllRUFk9;OTkzOTQ3QDMyMzAyZTM0MmUzMFE4WkQ3cHBlcm1XKzBPUFYyK1lpNXRWeXNPdlN3NXBFTFVqU3JLeGNzZ3c9;Mgo+DSMBMAY9C3t2VVhkQlFadVdJX3xLfUx0RWFab116dlNMYlpBNQtUQF1hSn5SdEFjWn1WdX1dQmFe;OTkzOTQ5QDMyMzAyZTM0MmUzMExQeWlqYmhQNDcwZnBacVNDNWhXRVhTVUtseWh0aTVLVFFSdkdHUXV3eE09;OTkzOTUwQDMyMzAyZTM0MmUzMEZ5Z2ZOSWgzZ0dOZXFYMXZFcHB2L3Y3amhnaHJvb21qeW9Yc0lYZnI2SGs9;OTkzOTUxQDMyMzAyZTM0MmUzME5DTWhxMUQwdlBFcGoxZkJTelZ5N1dIanpSaWdPUjVOQ09xeWMwSllRUFk9');
enableRipple(true);

export class SampleBase extends React.PureComponent {
    rendereComplete() {
        /**custom render complete function */
    }
    componentDidMount() {
        setTimeout(() => {
            this.rendereComplete();
        });
    }
}
export function updateSampleSection() { }
